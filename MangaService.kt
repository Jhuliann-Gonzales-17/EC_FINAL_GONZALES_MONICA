package com.monica.ec_final_gonzales_.data.retrofit

import com.monica.ec_final_gonzales_.data.response.MangaListResponse
import retrofit2.http.GET

interface MangaService {
    @GET("mangas/")
    suspend fun  getMangs(): MangaListResponse

    @GET("mangas/1/")
    suspend fun getFavorite(): MangaListResponse


}