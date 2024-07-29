package com.example.getirlite.model.product

data class Product(
    val attribute: String,
    val id: String,
    val imageURL: String?,
    val name: String,
    val price: Double,
    val priceText: String,
    val thumbnailURL: String?,
    val squareThumbnailURL: String?,
    var count: Int = 0
)