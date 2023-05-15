package com.hho.circleoflife.models.response

data class CircleResponseModel (
    val ID: Int? = null,
    val sectors: List<SectorResponseModel>? = null,
    val isLoading: Boolean = false,
    val message: String? = null,
)