package com.monica.ec_final_gonzales_.data.retrofit

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceInstance {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://nekos.best/api/v2/hug?amount=2")
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    fun getMangaService(): MangaService = retrofit.create(MangaService::class.java)
}