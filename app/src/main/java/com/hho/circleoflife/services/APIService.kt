package com.hho.circleoflife.services

import com.hho.circleoflife.models.UserInfoResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    @GET("me")
    suspend fun getUserInfo(): Response<UserInfoResponseModel>
}