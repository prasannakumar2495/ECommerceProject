package com.example.ecommerceproject.ui

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.ecommerceproject.databinding.FragmentSignUpBinding

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.signupPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("SignUP Fragment", "Data is inserted")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val singUp: Boolean = validateSignUpPage(
                    binding.signupEmailID.text.toString(),
                    binding.signupMobileNumber.text.toString(),
                    binding.signupPassword.text.toString(),
                )
                if (singUp) {
                    binding.signupBtn.isEnabled = true
                    binding.signupBtn.setOnClickListener {
                        val intent = Intent(activity, Dashboard::class.java)
                        activity?.startActivity(intent)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("SignUP Fragment", "Data is inserted")
            }

        })


    }

    private fun validateSignUpPage(
        emailID: String,
        mobileNumber: String,
        password: String,
    ): Boolean {
        return password.length > 5
                && android.util.Patterns.EMAIL_ADDRESS.matcher(emailID).matches()
                && android.util.Patterns.PHONE.matcher(mobileNumber).matches()
    }
}