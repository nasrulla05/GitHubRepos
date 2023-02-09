package com.akhbulatov.githubrepos.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "favorites_authors_table")
class FavoritesAuthors(
    @PrimaryKey
    val avatar : String,
    val fullName : String,
    val favoritesLogin : String
)
