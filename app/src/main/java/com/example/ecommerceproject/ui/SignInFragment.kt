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
import com.example.ecommerceproject.databinding.FragmentSignInBinding

class SignInFragment : Fragment() {
    lateinit var binding: FragmentSignInBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.enterPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                Log.d("SingIn Fragment", "Data is inserted")
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                val signIn = validateSignInPage(binding.enterMobileNumber.text.toString(),
                    binding.enterPassword.text.toString())
                if (signIn) {
                    binding.loginBtn.isEnabled = true
                    binding.loginBtn.setOnClickListener {
                        val intent = Intent(activity, Dashboard::class.java)
                        activity?.startActivity(intent)
                    }
                }
            }

            override fun afterTextChanged(p0: Editable?) {
                Log.d("SingIn Fragment", "Data is inserted")
            }

        })

    }

    private fun validateSignInPage(mobileNumber: String, password: String): Boolean {
        return android.util.Patterns.PHONE.matcher(mobileNumber)
            .matches() && password.length > 5
    }

}