package com.tasks.ecommerceadmin.data.api.model.product

data class Product (
    val name:String?=null,
    val currentPrice:Double?=0.0,
    val previousPrice:Double?=0.0,
    val categories:String?=null,
    val imageUrls:List<String>?= emptyList(),
    val quantity:Int?=0,
    val color:String?=null,
    val productUrl:String?=null,
    val brand:String?=null,
    val description:String?=null
)