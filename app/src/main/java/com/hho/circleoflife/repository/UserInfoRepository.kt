package com.hho.circleoflife.repository

import android.content.SharedPreferences
import com.google.gson.Gson
import com.hho.circleoflife.models.response.UserInfoResponseModel
import com.hho.circleoflife.utils.RetrofitProvider
import kotlinx.coroutines.flow.flow

class Repository(
    private val sharedPreferences: SharedPreferences

) {

    suspend fun getUserInfo() = flow {
        emit(UserInfoResponseModel(isLoading = true))
        val response = RetrofitProvider.getApiService(sharedPreferences).getUserInfo()
        if (response.isSuccessful) {
            emit(response.body()?.copy(isLoading = false))
        } else {
            val responseModel = Gson().fromJson(response.errorBody()?.string(), UserInfoResponseModel::class.java)
            emit(responseModel?.copy(isLoading = false))
        }
    }

}