package com.example.trab_final.view.employee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.trab_final.R
import com.example.trab_final.models.Orders
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class createOrders : AppCompatActivity() {

    private lateinit var nameClienteField : EditText
    private lateinit var bairroField : EditText
    private lateinit var ruaField : EditText
    private lateinit var numeroField : EditText
    private lateinit var codPedidoField : EditText
    private lateinit var entregadorField : EditText
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

        addButton.setOnClickListener {
            var entregador = ""
            for( x in _userList){
                if(x.email.toString() == entregadorField.text.toString())
                    entregador = x.uId.toString()
            }
            if(entregador != ""){
            addOrders(
                nameClienteField.text.toString(),
                bairroField.text.toString(),
                ruaField.text.toString(),
                numeroField.text.toString(),
                codPedidoField.text.toString(),
                entregador,
                _currentUser.companyId.toString()
            )
            }
            else{
                Toast.makeText(this, "User does not exist", Toast.LENGTH_LONG).show()
            }
        }
    }


    private fun addOrders(name: String?, street: String?, district: String?, number: String?, codProduct: String?, deliveryId: String?, companyId: String?){
        val newOrders : DatabaseReference = database.push()
        val idOrders = newOrders.key
        database = FirebaseDatabase.getInstance().getReference()

    database.child("orders").child(idOrders.toString()).setValue(Orders(idOrders,name, street,district, number, codProduct, deliveryId, companyId, "O produto encontra-se na loja."))

        val intent = Intent(this@createOrders, funcionarioListOrders::class.java)
        startActivity(intent)
    }
}