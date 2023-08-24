package com.monica.ec_final_gonzales_.data.response

import java.lang.Exception

sealed class MangaResponse<T>(data: T? = null, exception: Exception? = null){
    data class Success<T>(val data: T?): MangaResponse<T>(data)
    data class Error<T>(val exception: Exception?): MangaResponse<T>(null, exception)
}