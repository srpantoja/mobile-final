package com.example.trab_final.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.trab_final.R
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class createOrders : AppCompatActivity() {

    private lateinit var nameClienteField : EditText
    private lateinit var bairroField : EditText
    private lateinit var ruaField : EditText
    private lateinit var numeroField : EditText
    private lateinit var codPedidoField : EditText
    private lateinit var entregadorField : Spinner
    private lateinit var addButton : Button
    private lateinit var database : DatabaseReference
    private lateinit var auth : FirebaseAuth
    private lateinit var userList : ArrayList<User>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_orders)

        var userId = auth.currentUser?.uid

        database = FirebaseDatabase.getInstance().getReference().child("user")

        database.

    }
}