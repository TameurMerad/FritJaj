package com.example.bytecchallenge

import android.app.Activity
import android.provider.DocumentsContract.Root
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class OrdersAdapter(val orderList: MutableList<Order>,private val mainActivity: MainActivity):RecyclerView.Adapter<OrdersAdapter.MyAdapterViewHolder>() {
    inner class MyAdapterViewHolder (itemView:View):RecyclerView.ViewHolder(itemView){
        lateinit var done:ImageView
            init {
                done = itemView.findViewById<ImageView>(R.id.checkId)
                done.setOnClickListener {
                    orderList.removeAt(position)
                    mainActivity.saveData()
                    notifyDataSetChanged()
                    itemView.rootView.findViewById<TextView>(R.id.ordersCount).text = "Orders left : ${orderList.size}"
                }
            }

    }

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