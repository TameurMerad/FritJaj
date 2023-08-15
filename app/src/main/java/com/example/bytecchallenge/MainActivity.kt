package com.example.bytecchallenge

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val switcher = findViewById<SwitchMaterial>(R.id.switcherNightDay)
        var editor:SharedPreferences.Editor?=null
        var sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        var nightMode =sharedPreferences?.getBoolean("night",false)!!
        val orderList = mutableListOf<Order>()
        val recyclerViewJaj = findViewById<RecyclerView>(R.id.recyclerViewJaj)
        val adapter = OrdersAdapter(orderList)
        recyclerViewJaj.adapter = adapter
        recyclerViewJaj.layoutManager = LinearLayoutManager(this)

        if (nightMode){
            switcher.isChecked =true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        switcher.setOnCheckedChangeListener { compoundButton, state ->
        if (nightMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            editor = sharedPreferences?.edit()
            editor?.putBoolean("night",false)
        }else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            editor = sharedPreferences?.edit()
            editor?.putBoolean("night",true)

        }
        editor?.apply()
    }

        orderList.add(Order("Jaj: 11","Frit :12",R.drawable.frit))
        adapter.notifyDataSetChanged()








    }
}