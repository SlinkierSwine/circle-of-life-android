package com.hho.circleoflife.services

import com.hho.circleoflife.models.request.CircleRequestModel
import com.hho.circleoflife.models.response.CircleResponseModel
import com.hho.circleoflife.models.response.UserInfoResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIService {
    @GET("me")
    suspend fun getUserInfo(): Response<UserInfoResponseModel>
    @POST("circle")
    suspend fun getCircle(@Body getCircleData: CircleRequestModel): Response<CircleResponseModel>
}