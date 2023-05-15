package com.hho.circleoflife.repository

import com.google.gson.Gson
import com.hho.circleoflife.models.request.CircleRequestModel
import com.hho.circleoflife.models.response.CircleResponseModel
import com.hho.circleoflife.utils.RetrofitProvider
import kotlinx.coroutines.flow.flow

class CircleRepository {
    suspend fun getCircle(circleRequestData: CircleRequestModel) = flow {
        emit(CircleResponseModel(isLoading = true))
        val response = RetrofitProvider.getApiService().getCircle(circleRequestData)
        if (response.isSuccessful) {
            emit(response.body()?.copy(isLoading = false))
        } else {
            val responseModel = Gson().fromJson(response.errorBody()?.string(), CircleResponseModel::class.java)
            emit(responseModel?.copy(isLoading = false))
        }
    }
}