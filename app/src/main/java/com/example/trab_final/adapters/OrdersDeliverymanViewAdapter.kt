package com.example.trab_final.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.trab_final.R
import com.example.trab_final.models.Orders
import com.example.trab_final.view.employee.funcionarioListOrders
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class OrdersDeliverymanViewAdapter (val context: Context, val orderList: ArrayList<Orders>):
    RecyclerView.Adapter<OrdersDeliverymanViewAdapter.OrdersDeliverymanViewHolder>() {

    private lateinit var database: DatabaseReference

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersDeliverymanViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.orders_rows, parent, false)
        return OrdersDeliverymanViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersDeliverymanViewHolder, position: Int) {
        val currentOrder = orderList[position]

        holder.textName.text = currentOrder.name
        holder.textStreet.text = currentOrder.street
        holder.textDistrict.text = currentOrder.district
        holder.textNumber.text = currentOrder.number
        holder.textStatus.text = currentOrder.status
        database = FirebaseDatabase.getInstance().getReference().child("orders")


        holder.btnConfirm.setOnClickListener {
            currentOrder.status = "O Produto Foi Entregue"
            database.child(currentOrder.oId.toString()).setValue(currentOrder)
            Toast.makeText(this.context,"Confirm!!", Toast.LENGTH_LONG).show()
        }

        holder.btnCancel.setOnClickListener {
            currentOrder.status = "O Produto Foi Cancelado"
            database.child(currentOrder.oId.toString()).removeValue()
            Toast.makeText(this.context,"Cancel!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrdersDeliverymanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var textName: TextView = itemView.findViewById(R.id.orders_client_name)
        var textStreet: TextView = itemView.findViewById(R.id.orders_street_name)
        var textDistrict: TextView = itemView.findViewById(R.id.orders_district_name)
        var textNumber: TextView = itemView.findViewById(R.id.orders_number_name)
        var textStatus: TextView = itemView.findViewById(R.id.orders_status_name)
        var btnConfirm: Button = itemView.findViewById(R.id.confirm_orders)
        var btnCancel: Button = itemView.findViewById(R.id.cancel_orders)

    }

}