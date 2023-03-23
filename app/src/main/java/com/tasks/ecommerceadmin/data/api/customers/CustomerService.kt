package com.tasks.ecommerceadmin.data.api.customers


import com.tasks.ecommerceadmin.data.api.model.login.CustomerLogin
import com.tasks.ecommerceadmin.data.api.model.login.CustomerLoginResponse
import com.tasks.ecommerceadmin.data.api.model.product.Product
import com.tasks.ecommerceadmin.data.api.model.product.ProductResponse
import com.tasks.ecommerceadmin.data.api.model.register.CustomerRegister
import com.tasks.ecommerceadmin.data.api.model.register.CustomerRegisterResponse
import retrofit2.http.*

interface CustomerService {
    @POST("customers/")
    suspend fun registerCustomer(@Body customer: CustomerRegister): CustomerRegisterResponse

    @POST("customers/login")
    suspend fun signIn(@Body request: CustomerLogin): CustomerLoginResponse


    @GET("customers/customer")
    suspend fun getCustomer(@Header("Authorization") token:String,@Query("customer") customer: String,): CustomerRegisterResponse

    @POST("products/")
    suspend fun addProducts(@Header("Authorization") token:String,@Body products: Product):ProductResponse

}