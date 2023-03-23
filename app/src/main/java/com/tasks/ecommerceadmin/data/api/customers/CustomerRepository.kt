package com.tasks.ecommerceadmin.data.api.customers


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.tasks.ecommerceadmin.common.Results
import com.tasks.ecommerceadmin.data.api.model.login.CustomerLogin
import com.tasks.ecommerceadmin.data.api.model.login.CustomerLoginResponse
import com.tasks.ecommerceadmin.data.api.model.product.Product
import com.tasks.ecommerceadmin.data.api.model.product.ProductResponse
import com.tasks.ecommerceadmin.data.api.model.register.CustomerRegister
import com.tasks.ecommerceadmin.data.api.model.register.CustomerRegisterResponse
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class CustomerRepository @Inject constructor(
    private val service: CustomerService,
) {
    private val _registerResult = MutableLiveData<Results<CustomerRegisterResponse>>()
    val registerResult: LiveData<Results<CustomerRegisterResponse>> = _registerResult

    private val _addProductResult = MutableLiveData<Results<ProductResponse>>()
    val addProductResult: LiveData<Results<ProductResponse>> = _addProductResult



    suspend fun registerCustomer(email: String, password: String, firstName: String, lastName: String,userName:String) {
        try {
            val response = service.registerCustomer(
                CustomerRegister(
                firstName= firstName,
                lastName= lastName,
                login= userName,
                email= email,
                password= password,
                isAdmin=true
            )
            )
            _registerResult.postValue(Results.Success(response))
        } catch (e: IOException) {
            _registerResult.postValue(Results.Error(e.message ?: "Unknown error"))
        } catch (e: HttpException) {
            _registerResult.postValue(Results.Error(e.message ?: "Unknown error"))
        }
    }


    suspend fun signIn(email: String, password: String): CustomerLoginResponse {
        val request = CustomerLogin(email, password)
        return service.signIn(request)
    }


    suspend fun addProducts(token: String,title:String, currentPrice:Double,previousPrice:Double,categories:String,
                            imageUrls:List<String>, quantity:Int, color:String,
                            productUrl:String, brand:String, description:String){
        try {
            val response = service.addProducts(
                token,
              Product(
                  name=title,
                  currentPrice=currentPrice,
                  previousPrice =previousPrice,
                  categories = categories,
                  imageUrls=imageUrls,
                  quantity=quantity,
                  color=color,
                  productUrl=productUrl,
                  brand= brand,
                  description = description
              )
            )
            _addProductResult.postValue(Results.Success(response))
        } catch (e: IOException) {
            _addProductResult.postValue(Results.Error(e.message ?: "Unknown error"))
        } catch (e: HttpException) {
            _addProductResult.postValue(Results.Error(e.message ?: "Unknown error"))
        }

    }
    suspend fun getCustomer(token:String,customer:String): Results<CustomerRegisterResponse> {
        return try {
            val response = service.getCustomer(token,customer)
            Results.Success(response)
        } catch (e: Exception) {
            Results.Error(e.message ?: "An error occurred")
        }
    }





}