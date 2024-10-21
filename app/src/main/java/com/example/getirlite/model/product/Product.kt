package com.example.getirlite.model.product

import com.example.getirlite.view.components.bars.Searchable

data class Product(
    val attribute: String?,
    val id: String,
    val imageURL: String?,
    val name: String,
    val price: Double,
    val priceText: String,
    val thumbnailURL: String?,
    val squareThumbnailURL: String?,
    var count: Int = 0
): Searchable {
    override fun search(query: String): Boolean =query.isEmpty() or name.contains(query, ignoreCase = true) or name.equals(query, ignoreCase = true)
}