package com.example.trab_final.view.fragmentsEmpresa

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.example.trab_final.R
import com.example.trab_final.models.Orders
import com.example.trab_final.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*


class PedidosFragment : Fragment() {
    private lateinit var database: DatabaseReference
    private lateinit var list_viewPedidos: ListView
    private lateinit var auth: FirebaseAuth
    private lateinit var _companyList: ArrayList<String>
    private lateinit var _orderList: ArrayList<Orders>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_pedidos, container, false)

        list_viewPedidos = view.findViewById(R.id.list_viewPedidos)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().getReference().child("orders")
        Log.v("view", view.toString())
        val companyId = auth.currentUser?.uid
        _orderList = ArrayList<Orders>()
        _companyList = ArrayList<String>()


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _companyList.clear()
                for (postSnapshop in snapshot.children) {
                    var currentOrder = postSnapshop.getValue(Orders::class.java)
                    _orderList.add(currentOrder!!)
                    if(currentOrder.companyId==companyId){
                        _companyList.add(currentOrder.codProduct.toString()+"  |  "+currentOrder.name.toString())
                    }
                }
                val arrayAdapter: ArrayAdapter<String> = ArrayAdapter(
                    requireActivity(),android.R.layout.simple_list_item_1,_companyList
                )

                list_viewPedidos.adapter = arrayAdapter
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        return view
    }


}