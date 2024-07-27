package com.example.ecommercetask.data.remote.firestore

import retrofit2.Response
import retrofit2.http.GET

interface FakeStoreService {

    @GET("products")
    suspend fun getProducts(): Response<List<Product>>
}