package com.monica.ec_final_gonzales_.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.monica.ec_final_gonzales_.model.Manga
import androidx.room.*

@Dao
interface MangaDao {

    @Query("SELECT * FROM manga")
    fun getFavorites(): List<Manga>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFavorite(manga: Manga)

    @Delete
    suspend fun removeFavorite(manga: Manga)
}