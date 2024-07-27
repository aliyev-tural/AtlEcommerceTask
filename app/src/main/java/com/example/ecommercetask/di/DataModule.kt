package com.example.ecommercetask.di

import com.example.ecommercetask.data.remote.firestore.FakeStoreService
import com.example.ecommercetask.data.remote.firestore.Product
import com.example.ecommercetask.data.remote.firestore.ProductRepository
import com.google.android.play.integrity.internal.f
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Singleton
    @Provides
    fun provideFirebaseStore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun productApi():FakeStoreService{
         return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://fakestoreapi.com/")
            .build().create(FakeStoreService::class.java)
    }


    @Singleton
    @Provides
    fun productRepository(fakeStoreService: FakeStoreService,firestore: FirebaseFirestore):ProductRepository{
        return  ProductRepository(firestore, fakeStoreService)
    }


}