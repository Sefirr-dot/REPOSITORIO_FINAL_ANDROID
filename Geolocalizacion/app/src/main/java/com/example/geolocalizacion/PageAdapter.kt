package com.example.tabsfragments

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.geolocalizacion.FifthFragment
import com.example.geolocalizacion.FourthFragment
import com.example.geolocalizacion.MainActivity

class PageAdapter(fragmentActivity: MainActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 5
    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> FirstFragment()
            1 -> SecondFragment()
            2 -> ThirdFragment()
            3 -> FourthFragment()
            4 -> FifthFragment()
            else -> FirstFragment()
        }
    }

}