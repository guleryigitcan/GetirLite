package com.example.getirlite.view.fragments.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirlite.model.User
import com.example.getirlite.model.product.Product
import com.example.getirlite.model.product.ProductDatabase
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDatabase: CartDatabase) : ViewModel() {
    val cartItems = MutableStateFlow(listOf<Product>())
    val productCounts = MutableStateFlow<Map<String, Int>>(emptyMap())

    val totalAmount: StateFlow<Double> = productCounts.map {
        cartItems.value.sumOf { it.count * it.price }
    }.stateIn(viewModelScope, SharingStarted.Lazily, 0.0)

    val favouriteProducts = MutableStateFlow(listOf<Product>())


    private val gson: Gson = Gson()

    init {
        cartItems.value = cartDatabase.load()
        favouriteProducts.value = ProductDatabase.loadFavoriteProducts()
        updateProductCounts()
    }

    private fun updateProductCounts() {
        productCounts.value = cartItems.value.associateBy({ it.id }, { it.count })
    }

    fun addToCart(product: Product) {
        val updatedCart = cartItems.value.toMutableList()

        val cartItem = updatedCart.firstOrNull { it.id == product.id }
        if (cartItem != null) cartItem.count += 1
        else updatedCart.add(product.copy(count = 1))

        cartItems.value = updatedCart
        updateProductCounts()
        saveCartItems(updatedCart)
    }


    fun removeToCart(product: Product) {
        val updatedCart = cartItems.value.toMutableList()

        val cartItem = updatedCart.firstOrNull { it.id == product.id }
        if (cartItem != null && cartItem.count > 1) cartItem.count -= 1
        else updatedCart.remove(cartItem)

        cartItems.value = updatedCart
        updateProductCounts()
        saveCartItems(updatedCart)
    }

    fun clearCart() {
        val items = cartItems.value.filter { it.count > 0 } ?: return
        items.map { it.count = 0 }
        cartItems.value = emptyList()
        saveCartItems(emptyList())
        updateProductCounts()
    }

    fun addFavouriteProduct(product: Product) {
        val temp = favouriteProducts.value.toMutableList()
        if (temp.none { it.id == product.id }) {
            temp.add(product)
        }

        favouriteProducts.value = temp
        saveFavouriteProducts(temp)
    }

    fun removeFavoriteProduct(product: Product) {
        val temp = favouriteProducts.value.toMutableList()
        temp.removeAll { it.id == product.id}

        favouriteProducts.value = temp
        saveFavouriteProducts(temp)
    }

    fun getCount(product: Product): Int {
        return cartItems.value.firstOrNull{ it.id == product.id }?.count ?: 0
    }

    val isCartEmpty: Boolean get() = cartItems.value.isEmpty() ?: true

    fun isFavourite(product: Product) = favouriteProducts.value.any { it.id == product.id }

    private fun saveCartItems(products: List<Product>) {
        viewModelScope.launch {
            cartDatabase.save(products)
        }
    }

    private fun saveFavouriteProducts(products: List<Product>) {
        viewModelScope.launch {
            val json = gson.toJson(products)
            User.favouriteProducts.set(json)
        }
    }
}