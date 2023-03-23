package com.tasks.ecommerceadmin.presentation.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.jwt.JWT
import com.auth0.jwt.interfaces.DecodedJWT
import com.tasks.ecommerceadmin.common.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
   private var  dataStoreManager: DataStoreManager
) :ViewModel(){


    suspend fun getToken():String{
        return dataStoreManager.token.first()
    }


    suspend fun getRememberUser():Boolean{
        return dataStoreManager.isRemember.first()
    }

    suspend fun getFirstTime():Boolean{
        return dataStoreManager.isFirstTime.first()
    }
    suspend fun isAccessTokenExpired(): Boolean {
        return try{
            val token=getToken().removePrefix("Bearer ")
            val jwt: DecodedJWT = JWT.decode(token)
            val expirationTime: Date = jwt.expiresAt
            Date().after(expirationTime)
        }catch (e:Exception){
            Log.d("AccessTokenDecoded",e.message.toString())
            true
        }

    }

}