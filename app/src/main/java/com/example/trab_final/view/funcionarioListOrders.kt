package com.example.trab_final.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trab_final.R
import com.example.trab_final.adapters.OrdersFuncionarioViewAdapter
import com.example.trab_final.models.Orders
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class funcionarioListOrders : AppCompatActivity() {
    private lateinit var addButton: Button
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var ordersRecyclerView : RecyclerView
    private lateinit var ordersList: ArrayList<Orders>
    private lateinit var adapter: OrdersFuncionarioViewAdapter
    private lateinit var companyId: String
    private lateinit var currentUser : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_funcionario_list_orders)
        ordersList = ArrayList()
        currentUser = ""
        companyId = ""


        addButton = findViewById(R.id.addEntrega)

        ordersRecyclerView = findViewById(R.id.funcionarioRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(this@funcionarioListOrders)

        auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser?.uid.toString()
        database = FirebaseDatabase.getInstance().getReference()

        //getCompanyId()
        getListOrdersByCompanyId()
        addButton.setOnClickListener {
            val intent = Intent(this@funcionarioListOrders, createOrders::class.java)
            startActivity(intent)
        }
    }
    private fun getListOrdersByCompanyId(){
        database.child("orders").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (postSnapshop in snapshot.children) {
                    var _currentOrders = postSnapshop.getValue(Orders::class.java)
                    if(_currentOrders?.companyId == Login.companionUser?.companyId)
                        ordersList.add(_currentOrders!!)
                    println("NAME ABAIXO")
                    println(_currentOrders?.name)
                    println("NAME ACIMA")

                }
                adapter = OrdersFuncionarioViewAdapter(this@funcionarioListOrders, ordersList)
                ordersRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    private fun getCompanyId (){
        database.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshop in snapshot.children) {
                    var _currentUser = postSnapshop.getValue(User::class.java)
                    if(_currentUser?.uId == currentUser)
                        companyId = _currentUser.companyId.toString()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}