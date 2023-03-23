package com.tasks.ecommerceadmin.presentation.signup


import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceadmin.data.api.customers.CustomerRepository
import com.tasks.ecommerceadmin.data.api.model.register.CustomerRegisterResponse
import com.tasks.ecommerceadmin.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {

    var firstName =""
    var lastName =""
    var userName =""
    var password=""


    val registerResult: LiveData<Results<CustomerRegisterResponse>> = repository.registerResult
    fun registerCustomer(email: String, password: String, firstName: String, lastName: String,userName:String) {
        viewModelScope.launch {
            repository.registerCustomer(email, password, firstName,lastName,userName)
        }
    }







}