package com.example.ecommercetask.data.remote.firestore

data class Cart(
    val id:String?,
    val items:List<CartItem?>?
){
    constructor():this(null,null)
}