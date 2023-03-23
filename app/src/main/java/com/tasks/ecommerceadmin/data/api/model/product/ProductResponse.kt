package com.tasks.ecommerceadmin.data.api.model.product

import com.google.gson.annotations.SerializedName

data class ProductResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("quantity")
	val quantity: Int? = null,

	@field:SerializedName("color")
	val color: String? = null,

	@field:SerializedName("currentPrice")
	val currentPrice: Any? = null,

	@field:SerializedName("myCustomParam")
	val myCustomParam: String? = null,

	@field:SerializedName("itemNo")
	val itemNo: String? = null,

	@field:SerializedName("enabled")
	val enabled: Boolean? = null,

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("__v")
	val v: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("previousPrice")
	val previousPrice: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("categories")
	val categories: String? = null,

	@field:SerializedName("productUrl")
	val productUrl: String? = null,

	@field:SerializedName("brand")
	val brand: String? = null
)
