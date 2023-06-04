package com.pucuk.binar_challenge_ch_7.data.network

import com.pucuk.binar_challenge_ch_7.data.model.ResponseDetailFilm
import com.pucuk.binar_challenge_ch_7.data.model.ResponseFilm
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("movie/popular?api_key=fba632cdd6813ba053d5c4b18722f59c")
    suspend fun getAllFilmPopular() : ResponseFilm

    @GET("movie/{movie_id}")
    suspend fun getDetailFilm(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = "fba632cdd6813ba053d5c4b18722f59c"
    ): ResponseDetailFilm
}