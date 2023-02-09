package com.akhbulatov.githubrepos.models

import com.google.gson.annotations.SerializedName


class Repository(
    @SerializedName("id")
    val id : Int,
    @SerializedName("name")
    val name : String,
    @SerializedName("owner")
    val owner: Owner,
    @SerializedName("description")
    val descriptor: String
)


