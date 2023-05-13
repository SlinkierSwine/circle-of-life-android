package com.hho.circleoflife.models

import com.google.gson.annotations.SerializedName

data class UserInfoResponseModel(
    @SerializedName("id") val id: Int? = null,
    @SerializedName("username") val username: String? = null,
    @SerializedName("created_at") val createdAt: String? = null,
    @SerializedName("updated_at") val updatedAt: String? = null,
    val message: String? = null,
    val isLoading: Boolean = false,
)
