package com.akhbulatov.githubrepos.models

import com.google.gson.annotations.SerializedName

class ProfileInfo(
    @SerializedName("avatar_url")
    val avatarAva: String,
    @SerializedName("name")
    val fullName: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("bio")
    val bio : String?,
    @SerializedName("location")
    val location: String,
    @SerializedName("email")
    val email: String?,
    @SerializedName("followers")
    val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("public_repos")
    val public_repos: Int

    ): java.io.Serializable
