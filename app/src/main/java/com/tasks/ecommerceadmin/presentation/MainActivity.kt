package com.tasks.ecommerceadmin.presentation

import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal
import com.tasks.ecommerceadmin.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.initWithContext(this);
        OneSignal.setAppId("8be53908-ad56-488a-9a79-0f7fca4cdf1d");
        OneSignal.disablePush(false)
    }



}