package com.akhbulatov.githubrepos.network

import androidx.room.*
import com.akhbulatov.githubrepos.models.FavoritesAuthors

@Dao
interface FavoritesAuthorsDao {
    @Query("select*from favorites_authors_table")
     fun getAllAuthors(): List<FavoritesAuthors>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuthors(authors: FavoritesAuthors)

    @Delete
    suspend fun deleteAuthors(authors: FavoritesAuthors)


}