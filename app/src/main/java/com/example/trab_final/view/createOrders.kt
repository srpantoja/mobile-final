package com.example.trab_final.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.example.trab_final.R
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

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
    private lateinit var _userList : ArrayList<User>
    private lateinit var _deliveryList : ArrayList<User>
    private lateinit var _currentUser : User
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_orders)

        nameClienteField = findViewById(R.id.edit_name_client)
        bairroField = findViewById(R.id.edit_bairro)
        ruaField = findViewById(R.id.edit_rua)
        numeroField = findViewById(R.id.edit_number)
        codPedidoField = findViewById(R.id.edit_codProduto)
        entregadorField = findViewById(R.id.edit_Entregador)
        addButton = findViewById(R.id.btn_cad_pedido)

        auth = FirebaseAuth.getInstance()
        var userId = auth.currentUser?.uid
        _userList = ArrayList<User>()

        database = FirebaseDatabase.getInstance().getReference().child("user")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshop in snapshot.children) {
                    var currentUser = postSnapshop.getValue(User::class.java)
                    _userList.add(currentUser!!)
                    if(currentUser.uId == userId)
                        _currentUser = currentUser
                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        for( delivery in _userList){
            if(delivery.companyId == _currentUser.companyId)
                _deliveryList.add(delivery)
        }

        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_list_item_1, _deliveryList)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_item)
        entregadorField.adapter = adapterSpinner
    }
}