package com.example.getirlite.model.services

import com.example.getirlite.model.product.ProductResponse
import retrofit2.http.GET

interface Interface {
    @GET("products")
    suspend fun verticalProducts(): List<ProductResponse>?

    @GET("suggestedProducts")
    suspend fun suggestedProducts(): List<ProductResponse>?
}