package com.akhbulatov.githubrepos.network

import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubService {
    @GET("users/{login}")
    suspend fun getInfoUser(
        @Path("login") repositoryLogin : String
    ): ProfileInfo

    @GET("repositories/{id}")
    suspend fun getRepositoriesDetails(
        @Path("id") repositoryId: Int
    ): RepositoryDetails

    @GET("repositories")
    suspend fun getRepositories(): List<Repository>
}