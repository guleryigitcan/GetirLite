package com.example.getirlite.model.services

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object APIClient {
    private var retrofit: Retrofit? = null
    private const val apiURL: String = "https://65c38b5339055e7482c12050.mockapi.io/api/"

    val client: Retrofit?
        get() {

            val client = OkHttpClient()
                .newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build()

            if (retrofit == null)
                retrofit = Retrofit.Builder()
                    .baseUrl(apiURL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()

            return retrofit
        }

}