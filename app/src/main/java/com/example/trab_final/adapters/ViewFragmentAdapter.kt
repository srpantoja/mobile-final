package com.example.trab_final.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.trab_final.view.company.fragmentsEmpresa.EmployeeFragment
import com.example.trab_final.view.company.fragmentsEmpresa.OrdersFragment

class ViewFragmentAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OrdersFragment()
            }
            1 -> {
                EmployeeFragment()
            }
            else -> {
                Fragment()
            }
        }
    }
}