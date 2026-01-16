package com.arv.practica11.data

import android.content.Context
import android.content.SharedPreferences

class UserPreferences(context: Context){
    private val prefs: SharedPreferences=context.getSharedPreferences("mis_prefs", Context.MODE_PRIVATE)

    fun saveLastCategory(category:String){
        prefs.edit().putString("last_category",category).apply()
    }

    fun getLastCategory(): String{
        return prefs.getString("last_category","Todas")?:"Todas"
    }
}