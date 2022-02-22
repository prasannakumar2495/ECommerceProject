package com.example.ecommerceproject.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.ecommerceproject.ui.SignInFragment
import com.example.ecommerceproject.ui.SignUpFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                SignInFragment()
            }
            1 -> {
                SignUpFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}