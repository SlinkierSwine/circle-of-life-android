package com.hho.circleoflife.models

data class LoginResponseModel(
    val token: String? = null,
    val isLoading: Boolean = false
)
