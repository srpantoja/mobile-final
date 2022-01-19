package com.example.trab_final.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.trab_final.R

class funcionarioListOrders : AppCompatActivity() {
    private lateinit var addButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcionario_list_orders)

        addButton = findViewById(R.id.addEntrega)

        addButton.setOnClickListener {
            val intent = Intent(this@funcionarioListOrders, createOrders::class.java)
            startActivity(intent)
        }
    }
}