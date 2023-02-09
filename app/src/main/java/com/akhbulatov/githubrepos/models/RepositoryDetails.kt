package com.akhbulatov.githubrepos.models

import com.google.gson.annotations.SerializedName

class RepositoryDetails(
    @SerializedName("name")
    val name: String,

    @SerializedName("owner")
    val owner: Owner,

    @SerializedName("description")
    val description: String,

    @SerializedName("html_url")
    val link: String,

    @SerializedName("stargazers_count")
    val star: String,

    @SerializedName("forks_count")
    val reposts: String,

    @SerializedName("open_issues_count")
    val error: String

) : java.io.Serializable








