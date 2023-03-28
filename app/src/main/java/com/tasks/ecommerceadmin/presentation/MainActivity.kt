package com.tasks.ecommerceadmin.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.onesignal.OneSignal
import com.tasks.ecommerceadmin.R
import com.tasks.ecommerceadmin.common.Constants.APP_ID
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        OneSignal.initWithContext(this)
        OneSignal.setAppId(APP_ID)
        OneSignal.disablePush(false)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        this.finishAffinity()
    }

}