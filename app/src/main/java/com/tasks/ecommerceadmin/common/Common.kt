package com.tasks.ecommerceadmin.common

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import com.cloudinary.android.MediaManager

fun isEmail(text: String): Boolean {
        val regex = Regex("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}")
        return regex.matches(text)
}
 fun initMediaManager(context: Context) {
        val config: HashMap<String, String> = HashMap()
        config["cloud_name"] = Constants.CLOUDINARY_CLOUD_NAME
        config["api_key"] = Constants.CLOUDINARY_API_KEY
        config["api_secret"] = Constants.CLOUDINARY_API_SECRET
        MediaManager.init(context,config)
}

