package com.example.bytecchallenge

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter(val orderList: List<Order>):RecyclerView.Adapter<OrdersAdapter.MyAdapterViewHolder>() {
    inner class MyAdapterViewHolder (itemView:View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyAdapterViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.jaj_frit_item,p0,false)
        return MyAdapterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return orderList.size
    }

    override fun onBindViewHolder(holder: MyAdapterViewHolder, position: Int) {
        holder.itemView.apply {
            findViewById<TextView>(R.id.jajNumber).text = orderList[position].jajNum
            findViewById<TextView>(R.id.fritNumber).text = orderList[position].fritNum
            findViewById<ImageView>(R.id.maklaPic).setImageResource(orderList[position].pic)
        }    }


}