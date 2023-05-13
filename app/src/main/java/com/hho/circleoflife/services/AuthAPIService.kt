package com.hho.circleoflife.services

import com.hho.circleoflife.models.LoginRequestModel
import com.hho.circleoflife.models.LoginResponseModel
import com.hho.circleoflife.models.RegisterResponseModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPIService {
    @POST("login")
    fun login(@Body loginData: LoginRequestModel): Call<LoginResponseModel>

    @POST("register")
    fun register(): Call<RegisterResponseModel>
}