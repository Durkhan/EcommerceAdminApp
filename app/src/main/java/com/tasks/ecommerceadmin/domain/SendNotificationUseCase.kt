package com.tasks.ecommerceadmin.domain


import com.tasks.ecommerceadmin.common.Constants.APP_ID
import com.tasks.ecommerceadmin.common.Constants.REST_API_KEY
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.inject.Inject

class SendNotificationUseCase @Inject constructor(){
        var lastMessage: String? = null
         operator fun invoke(message: String) {
            val client = OkHttpClient()
            val json = JSONObject()

            try {
                val contents = JSONObject()
                contents.put("en", message)
                json.put("contents", contents)
                json.put("app_id", APP_ID)

                val includedSegments = JSONArray()
                includedSegments.put("All")
                json.put("included_segments", includedSegments)

                println(json)
            } catch (e: JSONException) {
                e.printStackTrace()
            }

            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val body = RequestBody.create(mediaType, json.toString())

            val request = Request.Builder()
                .url("https://onesignal.com/api/v1/notifications")
                .post(body)
                .addHeader("Authorization", "Basic $REST_API_KEY")
                .addHeader("Content-Type", "application/json")
                .build()

            try {
                val response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    println("Notification sent successfully")
                } else {
                    println("Notification send failed: " + response.body!!.string())
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

