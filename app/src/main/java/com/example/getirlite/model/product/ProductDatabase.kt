package com.example.getirlite.model.product

import com.example.getirlite.model.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ProductDatabase {
    private val gson: Gson = Gson()

    fun loadFavoriteProducts(): List<Product> {
        val json = User.favouriteProducts.string
        return try {
            val type = object : TypeToken<List<Product>>() {}.type
            gson.fromJson(json, type)
        } catch (_: Exception) {
            emptyList()
        }
    }

    fun addFavoriteProduct(product: Product) {
        val favoriteProducts = loadFavoriteProducts().toMutableList()
        if (favoriteProducts.none { it.id == product.id }) {
            favoriteProducts.add(product)
        }
        val json = gson.toJson(favoriteProducts)
        User.favouriteProducts.set(json)
    }

    fun removeFavoriteProduct(productId: String) {
        val favoriteProducts = loadFavoriteProducts().toMutableList()
        favoriteProducts.removeAll { it.id == productId }
        val json = gson.toJson(favoriteProducts)
        User.favouriteProducts.set(json)
    }

    fun isProductFavorite(productId: String): Boolean {
        return loadFavoriteProducts().any { it.id == productId }
    }
}