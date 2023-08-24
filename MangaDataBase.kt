package com.monica.ec_final_gonzales_.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.monica.ec_final_gonzales_.model.Manga

@Database(entities = [Manga::class], version = 1)
abstract class MangaDataBase : RoomDatabase() {
    abstract fun mangaDao(): MangaDao


    companion object{
        @Volatile
        private var instance: MangaDataBase?= null
        fun getDataBase(contex:Context) : MangaDataBase{
            if ( instance==null){
                synchronized(this){
                    instance= buildDatabase(contex)
                }
            }
            return instance!!
        }
        private fun buildDatabase(contex: Context): MangaDataBase?{
            return Room.databaseBuilder(
                contex.applicationContext,
                MangaDataBase::class.java,
                "manga_database"
            ).build()
        }
    }
}

