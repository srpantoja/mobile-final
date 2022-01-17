package com.example.trab_final.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.trab_final.fragmentsEmpresa.AtendenteFragment
import com.example.trab_final.fragmentsEmpresa.EntregadoresFragment
import com.example.trab_final.fragmentsEmpresa.PedidosFragment

class ViewPagerAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                PedidosFragment()
            }
            1 -> {
                EntregadoresFragment()
            }
            2 -> {
                AtendenteFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}