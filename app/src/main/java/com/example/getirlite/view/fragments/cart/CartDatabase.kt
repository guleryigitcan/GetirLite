package com.example.getirlite.view.fragments.cart

import android.content.Context
import android.content.SharedPreferences
import com.example.getirlite.model.product.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDatabase @Inject constructor(@ApplicationContext context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("cart_prefs", Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()
    private val gson: Gson = Gson()

    fun load(): List<Product> {
        val json = sharedPreferences.getString(CART_ITEMS_KEY, null)
        return if (json != null) {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    fun save(cartItems: List<Product>) {
        val json = gson.toJson(cartItems)
        editor.putString(CART_ITEMS_KEY, json)
        editor.apply()
    }

    companion object {
        const val CART_ITEMS_KEY = "cartItems"
    }
}