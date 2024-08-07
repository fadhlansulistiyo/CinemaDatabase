package com.fadhlansulistiyo.cinemadatabase.core.data.remote.response

import com.google.gson.annotations.SerializedName

data class ProductionCompaniesResponse(

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("name")
    val name: String? = null

)
