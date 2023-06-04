package com.pucuk.binar_challenge_ch_7.data.model


import com.google.gson.annotations.SerializedName

data class ResponseFilm(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<Result>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)