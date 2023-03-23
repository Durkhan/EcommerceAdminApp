package com.tasks.ecommerceadmin.presentation.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tasks.ecommerceadmin.common.DataStoreManager
import com.tasks.ecommerceadmin.data.api.customers.ApiAuthenticator
import com.tasks.ecommerceadmin.data.api.customers.CustomerRepository
import com.tasks.ecommerceadmin.data.api.model.login.CustomerLoginResponse
import com.tasks.ecommerceadmin.common.Results
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInRepository: CustomerRepository,
    private val apiAuthenticator: ApiAuthenticator,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _signInResult = MutableLiveData<Results<CustomerLoginResponse>>()
    val signInResult: LiveData<Results<CustomerLoginResponse>> = _signInResult

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = signInRepository.signIn(email, password)
                setAuthToken(response.token)
                _signInResult.value = Results.Success(response)
            } catch (e: Exception) {
                _signInResult.value = Results.Error(e.message.toString())
            }
        }
    }
    private suspend fun setAuthToken(token: String?) {
        apiAuthenticator.setAuthToken(token)
        dataStoreManager.saveToken(token.toString())
    }

    fun saveRememberMe(boolean: Boolean){
        viewModelScope.launch {
            dataStoreManager.setRemember(boolean)
        }
    }

}
