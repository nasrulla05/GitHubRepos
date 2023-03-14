package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoViewModel: ViewModel() {
    lateinit var getInfoAboutDetails: Call<ProfileInfo>
    val profileInfoLiveData = MutableLiveData<ProfileInfo>()
    val failureLiveData = MutableLiveData<String>()

    fun loadRepositoriesLogin(repositoryLogin: String) {
        getInfoAboutDetails =
            GitHubReposApplication.gitHubService.getInfoUser(repositoryLogin)

        getInfoAboutDetails.enqueue(object : Callback<ProfileInfo> {
            override fun onResponse(
                call: Call<ProfileInfo>,
                response: Response<ProfileInfo>
            ) {
                val profileInfo: ProfileInfo? = response.body()
                profileInfoLiveData.value = profileInfo

            }

            override fun onFailure(call: Call<ProfileInfo>, t: Throwable) {
                failureLiveData.value = t.message
            }
        })
    }

    fun onFavoritesAuthorsClick(){
        val profileInfo = profileInfoLiveData.value!!
            val authors = FavoritesAuthors(
                // Добавление в БД
                profileInfo.avatarAva,
                profileInfo.fullName,
                profileInfo.login
            )
            val authorsDao: FavoritesAuthorsDao =
                GitHubReposApplication.appDatabase.authorsDao()
            authorsDao.insertAuthors(authors)
        }

    override fun onCleared() {
        super.onCleared()
        getInfoAboutDetails.cancel()
    }
}
