package com.example.getirlite.view.fragments

import com.example.getirlite.model.product.Product

interface StickyItemInteractionListener {
    fun onAddToCart(product: Product)
    fun onRemoveFromCart(product: Product)
}