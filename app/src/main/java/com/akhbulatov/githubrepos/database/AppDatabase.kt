package com.akhbulatov.githubrepos.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao


@Database(
    entities = arrayOf(FavoritesAuthors::class),
    version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authorsDao(): FavoritesAuthorsDao
}
