package com.example.trab_final.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.trab_final.R
import com.example.trab_final.models.Orders

class OrdersMotoqueiroViewAdapter (val context: Context, val orderList: ArrayList<Orders>):
    RecyclerView.Adapter<OrdersMotoqueiroViewAdapter.OrdersMotoqueiroViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersMotoqueiroViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.orders_rows, parent, false)
        return OrdersMotoqueiroViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersMotoqueiroViewHolder, position: Int) {
        val currentOrder = orderList[position]

        holder.textName.text = currentOrder.name
        holder.textStreet.text = currentOrder.street
        holder.textDistrict.text = currentOrder.district
        holder.textNumber.text = currentOrder.number

        holder.btnConfirm.setOnClickListener {
            Toast.makeText(this.context,"Confirm!!", Toast.LENGTH_LONG).show()
        }

        holder.btnCancel.setOnClickListener {
            Toast.makeText(this.context,"Cancel!!", Toast.LENGTH_LONG).show()
        }
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrdersMotoqueiroViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var textName: TextView = itemView.findViewById(R.id.orders_client_name)
        var textStreet: TextView = itemView.findViewById(R.id.orders_street_name)
        var textDistrict: TextView = itemView.findViewById(R.id.orders_district_name)
        var textNumber: TextView = itemView.findViewById(R.id.orders_number_name)
        var btnConfirm: Button = itemView.findViewById(R.id.confirm_orders)
        var btnCancel: Button = itemView.findViewById(R.id.cancel_orders)

    }

}