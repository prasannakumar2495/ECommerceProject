@file:Suppress("DEPRECATION")

package com.example.ecommerceproject.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.ecommerceproject.R
import com.example.ecommerceproject.databinding.ActivityDashboardBinding
import com.example.ecommerceproject.util.Global
import com.example.ecommerceproject.util.Global.Companion.LOCATION_REQUEST_PERMISSION_REQUEST_CODE
import com.google.android.gms.location.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.util.*

class Dashboard : AppCompatActivity() {
    private lateinit var dashboardContext: Context
    private lateinit var binding: ActivityDashboardBinding

    companion object {
        var locationData = listOf<Any>()
    }

    @SuppressLint("InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //if the user want to use this class content in another class, we can do it as below.
        dashboardContext = applicationContext

        val bottomNavigationView = binding.bottomNavigationView
        val navController = findNavController(R.id.fragmentContainerView)
        bottomNavigationView.setupWithNavController(navController)

        /* Below code is to ask for the location permission. */
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                LOCATION_REQUEST_PERMISSION_REQUEST_CODE
            )

        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val async = async { getCurrentLocation() }
                async.await()
                println("The size of locationData is: ${locationData.size}")
                for (i in locationData) {
                    println("The value of latitude and longitude are: $i")
                }
            }
        }

        /* Navigation to the profile screen. */
        binding.profilePicture.setOnClickListener {
            val intent = Intent(this, ProfileCreationScreen::class.java)
            startActivity(intent)
        }

        /* manually entering current address. */
        addressChange()

    }

    private fun addressChange() {
        binding.dashboardLocation.setOnClickListener {
            val popupMenu = PopupMenu(this, it)
            popupMenu.inflate(R.menu.dashboardlocation)
            popupMenu.setOnMenuItemClickListener { menuItemID ->
                when (menuItemID.itemId) {
                    R.id.dashboardLocation_NewAddress -> {
                        newAddress()
                    }
                    R.id.dashboardLocation_UpdateAddress -> {
                        updateAddress()
                    }
                }
                true
            }
            popupMenu.show()
        }
    }

    private fun updateAddress() {
        Toast.makeText(this, "Update the current address", Toast.LENGTH_SHORT).show()
    }

    @SuppressLint("SetTextI18n")
    private fun newAddress() {
        //inflate the layout
        val mDialogView =
            LayoutInflater.from(this).inflate(R.layout.entercurrentaddresslayout, null)
        //AlertDialogBuilder
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)
            .setTitle("Enter Delivery Address")
        //show Dialog
        val mAlertDialog = mBuilder.show()
        //click on SAVE Button
        mDialogView.findViewById<Button>(R.id.popUpSaveButton).setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
            //get text from Edit Texts of alert layout
            val houseNo =
                mDialogView.findViewById<EditText>(R.id.popUpHouseNumber).text.toString()
            val streetName =
                mDialogView.findViewById<EditText>(R.id.popUpStreetName).text.toString()
            val cityVillage =
                mDialogView.findViewById<EditText>(R.id.popUpCity_Village).text.toString()

            //set the input text in the UI
            binding.dashboardLocation.text = "$houseNo, $streetName, $cityVillage"
        }

        mDialogView.findViewById<Button>(R.id.popUpCancelButton).setOnClickListener {
            //dismiss dialog
            mAlertDialog.dismiss()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_REQUEST_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation()
            } else {
                Toast.makeText(this, "Permission is Denied", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentLocation() {
        val locationRequest = LocationRequest()
        locationRequest.interval = 1000
        locationRequest.fastestInterval = 5000
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        //getting the address
        val geocoder = Geocoder(this, Locale.getDefault())
        var addresses: List<Address>

        Global.checkSelfPermission(this)

        LocationServices.getFusedLocationProviderClient(this)
            .requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(this@Dashboard)
                            .removeLocationUpdates(this)
                        if (locationResult.locations.size > 0) {
                            val locIndex = locationResult.locations.size - 1

                            val latitude = locationResult.locations[locIndex].latitude
                            val longitude = locationResult.locations[locIndex].longitude

                            locationData = listOf(latitude, longitude)

                            addresses = geocoder
                                .getFromLocation(latitude, longitude, 1)

                            /* To show the current user locality... */
                            Toast.makeText(this@Dashboard,
                                addresses[0].postalCode,
                                Toast.LENGTH_SHORT).show()

                            val address: String = addresses[0].getAddressLine(0)
                            address.also { binding.dashboardLocation.text = it }
                        }
                    }
                },
                Looper.getMainLooper()
            )
    }
}