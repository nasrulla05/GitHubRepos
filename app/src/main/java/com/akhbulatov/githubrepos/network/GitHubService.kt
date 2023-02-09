package com.akhbulatov.githubrepos.network

import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{login}")
    fun getInfoUser(
        @Path("login") repositoryLogin : String
    ): Call<ProfileInfo>

    @GET("repositories/{id}")
    fun getRepositoriesDetails(
        @Path("id") repositoryId: Int
    ): Call<RepositoryDetails>

    @GET("repositories")
    fun getRepositories(

    ): Call<List<Repository>>
}