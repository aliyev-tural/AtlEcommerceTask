package com.example.ecommercetask.data.remote.firestore

import android.util.Log
import com.example.ecommercetask.data.Resource
import com.example.ecommercetask.data.performOperation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ProductRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val fakeStoreService: FakeStoreService
) {
    suspend fun getProducts() =
         performOperation {
            val collectionRef = firestore.collection("E-commerceDB")
            val querySnapshot = collectionRef.get().await()
            val products = mutableListOf<Product>()
            for (document in querySnapshot.documents) {
                val product = document.toObject(Product::class.java)
                Log.d("PRODUCT",product.toString())
                if (product != null) {
                    products.add(product)
                } else {
                    Log.w("Firestore", "Error converting document to Product object")
                }
            }
            products
        }


    suspend fun getProductByDocumentId(documentId:String) =
         performOperation {
            val documentRef = firestore.collection("E-commerceDB").document(documentId)
            val snapshot = documentRef.get().await()
            snapshot.toObject(Product::class.java)
         }


    suspend fun getProductsFromFakeStore() =
       performOperation { fakeStoreService.getProducts().body()}


    suspend fun populateDatabase(){
        val result = getProductsFromFakeStore()
        if(result is Resource.Success){
            val collectionRef = firestore.collection("products")
            performOperation {
                result.data?.forEach { product ->
                    collectionRef.document(product.id.toString()).set(product).await()
                }
            }
        }
        if(result is Resource.Error) Log.d("ERROR_PRODUCT_FETCHING","${result.message}")
    }


    suspend fun addProductToCart(productId:String,userId:String)=
         performOperation {
            val collectionRef = firestore.collection("carts").document(userId).collection("items")
            collectionRef.document(productId.toString()).set(CartItem(productId))
         }

}