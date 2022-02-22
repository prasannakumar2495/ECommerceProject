package com.example.ecommerceproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ecommerceproject.databinding.ActivityProfileCreationScreenBinding

class ProfileCreationScreen : AppCompatActivity() {
    lateinit var binding: ActivityProfileCreationScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileCreationScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}