package com.hho.circleoflife.utils

import android.content.SharedPreferences
import com.hho.circleoflife.services.APIService
import com.hho.circleoflife.services.AuthAPIService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitProvider {
    val BASE_URL = "http://10.0.2.2:8080/api/"
    val JWT_TOKEN = "token"

    private fun getRetrofit(
        sharedPreferences: SharedPreferences
    ): Retrofit {
        val authInterceptor = AuthInterceptor(sharedPreferences = sharedPreferences)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    fun getAuthAPIService() : AuthAPIService {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(AuthAPIService::class.java)
    }

    fun getApiService(sharedPreferences: SharedPreferences): APIService
            = getRetrofit(sharedPreferences).create(APIService::class.java)
}