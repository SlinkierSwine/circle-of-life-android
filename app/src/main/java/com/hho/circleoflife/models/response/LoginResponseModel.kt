package com.hho.circleoflife.models.response

data class LoginResponseModel(
    val token: String? = null,
    val isLoading: Boolean = false
)
