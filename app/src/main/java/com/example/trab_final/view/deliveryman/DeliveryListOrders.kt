package com.example.trab_final.view.deliveryman

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.trab_final.R
import com.example.trab_final.adapters.OrdersDeliverymanViewAdapter
import com.example.trab_final.models.Orders
import com.example.trab_final.view.authpage.Login
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class DeliveryListOrders : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var ordersRecyclerView : RecyclerView
    private lateinit var ordersList: ArrayList<Orders>
    private lateinit var adapter: OrdersDeliverymanViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_deliveryman_list_orders)
        ordersList = ArrayList()

        ordersRecyclerView = findViewById(R.id.deliveryListRecyclerView)
        ordersRecyclerView.layoutManager = LinearLayoutManager(this)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference()

        getListOrdersByCompanyId()
    }

    private fun getListOrdersByCompanyId(){
        database.child("orders").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ordersList.clear()
                for (postSnapshop in snapshot.children) {
                    var _currentOrders = postSnapshop.getValue(Orders::class.java)
                    if(_currentOrders?.deliveryId == Login.companionUser?.uId)
                        ordersList.add(_currentOrders!!)
                }
                adapter = OrdersDeliverymanViewAdapter(this@DeliveryListOrders, ordersList)
                ordersRecyclerView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}