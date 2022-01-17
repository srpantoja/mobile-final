package com.example.trab_final.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.trab_final.R
import com.example.trab_final.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainEmpresa : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_empresa)

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)

        val adapter = ViewPagerAdapter(supportFragmentManager,lifecycle)

        viewPager2.adapter=adapter
        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                0 -> {
                    tab.text="Pedidos"
                }
                1 -> {
                    tab.text="Funcionarios"
                }
            }
        }.attach()
    }
}