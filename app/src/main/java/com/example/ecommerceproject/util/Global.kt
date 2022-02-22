package com.example.ecommerceproject.util

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.os.Looper
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.example.ecommerceproject.ui.Dashboard
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.*
import kotlin.collections.ArrayList
import kotlin.coroutines.coroutineContext

@Suppress("DEPRECATION")
class Global {

    companion object {
        const val LOCATION_REQUEST_PERMISSION_REQUEST_CODE = 101

        fun checkSelfPermission(
            context: Context,
        ) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
    }
}
