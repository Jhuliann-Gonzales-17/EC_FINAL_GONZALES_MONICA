package com.monica.ec_final_gonzales_.data.repository

import com.monica.ec_final_gonzales_.data.db.MangaDao
import com.monica.ec_final_gonzales_.data.response.MangaListResponse
import com.monica.ec_final_gonzales_.data.response.MangaResponse
import com.monica.ec_final_gonzales_.data.retrofit.ServiceInstance
import com.monica.ec_final_gonzales_.model.Manga

class MangaRepository (val mangaDao: MangaDao? = null ){
    suspend fun getMangs(): MangaResponse<MangaListResponse>{
        return try {
            val response = ServiceInstance.getMangaService().getMangs()
            MangaResponse.Success(response)
        }catch (e: Exception){
            MangaResponse.Error(e)
        }
    }

    suspend fun addFavorite(manga: Manga){
        mangaDao?.let {
            it.removeFavorite(manga)
        }
    }

    suspend fun removeFavorite(manga: Manga){
        mangaDao?.let {
            it.removeFavorite(manga)
        }
    }


    fun getFavorites(): List<Manga> {
        mangaDao?.let {
            return it.getFavorites()
        } ?: kotlin.run {
            return listOf()
        }
    }

}