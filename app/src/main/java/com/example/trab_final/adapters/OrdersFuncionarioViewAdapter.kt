package com.example.trab_final.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.trab_final.R
import com.example.trab_final.models.Orders

class OrdersFuncionarioViewAdapter(val context: Context, val orderList: ArrayList<Orders>):
    Adapter<OrdersFuncionarioViewAdapter.OrdersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.funcionario_orders_row, parent, false)
        return OrdersViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentOrder = orderList[position]

        holder.textName.text = currentOrder.name
        holder.textStreet.text = currentOrder.street
        holder.textDistrict.text = currentOrder.district
        holder.textNumber.text = currentOrder.number
        holder.textStatus.text = currentOrder.status

    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    class OrdersViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){

        var textName: TextView = itemView.findViewById(R.id.orders_client_name_fun)
        var textStreet: TextView  = itemView.findViewById(R.id.orders_street_name_fun)
        var textDistrict: TextView  = itemView.findViewById(R.id.orders_district_name_fun)
        var textNumber: TextView  = itemView.findViewById(R.id.orders_number_name_fun)
        var textStatus: TextView  = itemView.findViewById(R.id.orders_status_name_fun)

    }
}