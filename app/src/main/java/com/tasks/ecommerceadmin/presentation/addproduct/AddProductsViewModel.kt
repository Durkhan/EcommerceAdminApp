package com.tasks.ecommerceadmin.presentation.addproduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceadmin.common.Constants.APP_ID
import com.tasks.ecommerceadmin.common.Constants.REST_API_KEY
import com.tasks.ecommerceadmin.common.DataStoreManager
import com.tasks.ecommerceadmin.common.Results
import com.tasks.ecommerceadmin.data.api.customers.CustomerRepository
import com.tasks.ecommerceadmin.data.api.model.product.ProductResponse
import com.tasks.ecommerceadmin.domain.SendNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddProductsViewModel @Inject constructor(
    private val repository: CustomerRepository,
    private val sendNotificationUseCase: SendNotificationUseCase,
    private val dataStoreManager: DataStoreManager,
    ):ViewModel() {

    val addProductResult: LiveData<Results<ProductResponse>> = repository.addProductResult

    fun addProduct(
        title: String, currentPrice: Double, previousPrice: Double, categories: String,
        imageUrls: List<String>, quantity: Int, color: String,
        productUrl: String, brand: String, description: String
    ) {
        viewModelScope.launch {
            repository.addProducts(
                dataStoreManager.token.first(),
                title,
                currentPrice,
                previousPrice,
                categories,
                imageUrls,
                quantity,
                color,
                productUrl,
                brand,
                description
            )
        }
    }

    fun sendNotification(message: String) {
        viewModelScope.launch(Dispatchers.IO) {
            sendNotificationUseCase(message)
        }
    }
}



