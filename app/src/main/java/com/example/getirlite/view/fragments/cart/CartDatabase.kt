package com.example.getirlite.view.fragments.cart

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.example.getirlite.model.User
import com.example.getirlite.model.product.Product
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartDatabase @Inject constructor() {
    private val gson: Gson = Gson()

    fun load(): List<Product> {
        val json = User.cartItems.string

        return try {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(json, type)
        } catch (_: Exception) {
            emptyList()
        }

    }

    fun save(cartItems: List<Product>) {
        val json = gson.toJson(cartItems)
        User.cartItems.set(json)
    }
}