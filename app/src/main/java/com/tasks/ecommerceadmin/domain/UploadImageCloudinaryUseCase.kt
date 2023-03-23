package com.tasks.ecommerceadmin.domain

import android.content.Context
import android.net.Uri
import android.util.Log
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.tasks.ecommerceadmin.common.Constants.CLOUDINARY_CLOUD_UPLOAD_PRESET
import com.tasks.ecommerceadmin.common.listener.UploadImageCallback
import javax.inject.Inject


class UploadImageCloudinaryUseCase @Inject constructor() {
   operator fun invoke(
        uri: Uri,
        callback: UploadImageCallback
    ){
       MediaManager.get().upload(uri).unsigned(CLOUDINARY_CLOUD_UPLOAD_PRESET).callback(object :UploadCallback{
            override fun onStart(requestId: String?) {}
            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {}

            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                if (resultData != null) {
                    val publicId = resultData["public_id"] as String
                    val url = MediaManager.get().url().generate(publicId)
                    callback.onUploadSuccess(url)
                }
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.d("ErrorInfo",error?.description.toString())
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {}
        }).dispatch()

   }
}