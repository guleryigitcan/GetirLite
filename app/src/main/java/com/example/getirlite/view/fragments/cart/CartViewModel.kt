package com.example.getirlite.view.fragments.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getirlite.model.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val cartDatabase: CartDatabase) : ViewModel() {
    val cartItems: MutableLiveData<List<Product>> = MutableLiveData(listOf())

    init {
        cartItems.value = cartDatabase.load()
    }

    fun addToCart(product: Product) {
        val updatedCart = cartItems.value?.toMutableList() ?: mutableListOf()

        // I didn't use .contains because i add count to product class
        if (updatedCart.any { it.id == product.id }) {
            updatedCart.first{ it.id == product.id }.count += 1
            cartItems.value = updatedCart
            saveCartItems(updatedCart)
            return
        }
        product.count += 1
        updatedCart.add(product)
        cartItems.value = updatedCart
        saveCartItems(updatedCart)
    }

    fun removeToCart(product: Product) {
        val updatedCart = cartItems.value?.toMutableList() ?: mutableListOf()

        if (updatedCart.any { it.id == product.id } && updatedCart.first { it.id == product.id}.count > 1) {
            updatedCart.first{ it.id == product.id }.count -= 1
            cartItems.value = updatedCart
            saveCartItems(updatedCart)
            return
        }

        if (product.count != 0) product.count -= 1
        updatedCart.remove(updatedCart.first { it.id == product.id })
        cartItems.value = updatedCart
        saveCartItems(updatedCart)
    }

    fun clearCart() {
        val list = emptyList<Product>()
        cartItems.value = list
        saveCartItems(emptyList())
    }

    fun getCount(product: Product): Int {
        return cartItems.value?.firstOrNull{ it.id == product.id }?.count ?: 0
    }

    val totalAmount: Double get() = cartItems.value?.sumOf { it.count * it.price } ?: 0.0

    val isCartEmpty: Boolean get() = cartItems.value?.isEmpty() ?: true


    private fun saveCartItems(products: List<Product>) {
        viewModelScope.launch {
            cartDatabase.save(products)
        }
    }
}