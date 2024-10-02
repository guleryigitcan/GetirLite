package com.example.getirlite.model

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.example.getirlite.MainActivity

object UserData {
    private var _data: SharedPreferences? = null

    val data: SharedPreferences
        get() {
            try {
                _data?.let { return it }
            } catch (_: Exception) {
            }
            MainActivity.instance.getSharedPreferences(MainActivity.instance.packageName + "_preferences", Context.MODE_PRIVATE)?.let {
                _data = it
                return it
            }
            return _data!!
        }

    val editor: SharedPreferences.Editor get() = data.edit()

    fun data(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    fun editor(context: Context): SharedPreferences.Editor = data(context).edit()
    fun start(context: Context) {
//        _data = PreferenceManager.getDefaultSharedPreferences(context)
        _data = context.getSharedPreferences("${context.packageName}_preferences", Context.MODE_PRIVATE)
    }
}