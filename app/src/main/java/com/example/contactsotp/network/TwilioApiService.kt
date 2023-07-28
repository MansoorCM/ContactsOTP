package com.example.contactsotp.network

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.http.*

private const val BASE_URL =
    "https://api.twilio.com/2010-04-01/"

private val retrofit = Retrofit.Builder()
    .baseUrl(BASE_URL)
    .build()

interface TwilioApiService {
    @FormUrlEncoded
    @POST("Accounts/{ACCOUNT_SID}/Messages.json")
    fun sendMessage(
        @Path("ACCOUNT_SID") accountSId: String,
        @Header("Authorization") signature: String,
        @FieldMap smsData: HashMap<String, String>
    ): Call<ResponseBody?>
}

object TwilioApi {
    val retrofitService: TwilioApiService by lazy { retrofit.create(TwilioApiService::class.java) }
}