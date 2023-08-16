package com.example.bytecchallenge

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.Theme_ByteCchallenge)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        val switcher = findViewById<SwitchMaterial>(R.id.switcherNightDay)
        var editor: SharedPreferences.Editor? = null
        var sharedPreferences = getSharedPreferences("MODE", Context.MODE_PRIVATE)
        var nightMode = sharedPreferences?.getBoolean("night", false)!!
        val orderList = mutableListOf<Order>()
        val recyclerViewJaj = findViewById<RecyclerView>(R.id.recyclerViewJaj)
        val adapter = OrdersAdapter(orderList)
        recyclerViewJaj.adapter = adapter
        recyclerViewJaj.layoutManager = LinearLayoutManager(this)
        val orderCount = findViewById<TextView>(R.id.ordersCount)
        orderCount.text = "Orders left : ${orderList.size}"

        val dialogBinding2 = layoutInflater.inflate(R.layout.no_internet, null)
        val myDialog2 = Dialog(this)
        myDialog2.setContentView(dialogBinding2)
        myDialog2.setCancelable(true)
        myDialog2.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (!isNetworkAvailable()) {
            myDialog2.show()
        }



        val dialogBinding = layoutInflater.inflate(R.layout.add_order_dialog, null)
        val myDialog = Dialog(this)
        myDialog.setContentView(dialogBinding)
        myDialog.setCancelable(true)
        myDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        if (nightMode) {
            switcher.isChecked = true
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        switcher.setOnCheckedChangeListener { compoundButton, state ->
            if (nightMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", false)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                editor = sharedPreferences?.edit()
                editor?.putBoolean("night", true)

            }
            editor?.apply()
        }

//        orderList.add(Order("Jaj: 11", "Frit :12", R.drawable.frit))
        adapter.notifyDataSetChanged()

        fab.setOnClickListener {
            myDialog.show()
            val cancel = dialogBinding.findViewById<Button>(R.id.cancelBtn)
            val add = dialogBinding.findViewById<Button>(R.id.addBtn)
            val frit = dialogBinding.findViewById<EditText>(R.id.fritNumberInput)
            val jaj = dialogBinding.findViewById<EditText>(R.id.jajNumberInput)

            cancel.setOnClickListener {
                myDialog.dismiss()
            }
            add.setOnClickListener {
                if ((jaj.text.isEmpty()||jaj.text.toString()=="0")&& (frit.text.isEmpty()||frit.text.toString()=="0")){
                    jaj.error = "empty field"
                    frit.error = "empty field"
                }else{
                if (jaj.text.toString() == "0" && frit.text.toString() != "0") {
                    orderList.add(Order("Jaj : ${jaj.text}", "Frit : ${frit.text}", R.drawable.frit))
                    orderCount.text = "Orders left : ${orderList.size}"
                    myDialog.dismiss()
                } else if (jaj.text.toString() != "0" && frit.text.toString() == "0") {
                    orderList.add(Order("Jaj : ${jaj.text}", "Frit : ${frit.text}", R.drawable.jaj))
                    orderCount.text = "Orders left : ${orderList.size}"
                    myDialog.dismiss()
                } else {
                    orderList.add(Order("Jaj : ${jaj.text}", "Frit : ${frit.text}", R.drawable.jaj_wfrit))
                    orderCount.text = "Orders left : ${orderList.size}"
                    myDialog.dismiss()
                }
                adapter.notifyDataSetChanged()
            }
                jaj.text.clear()
                frit.text.clear()

            }

        }



    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            return networkCapabilities != null &&
                    (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET))
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}