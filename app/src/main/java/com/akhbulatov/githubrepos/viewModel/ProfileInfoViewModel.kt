package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao
import kotlinx.coroutines.launch

class ProfileInfoViewModel : ViewModel() {
    val profileInfoLiveData = MutableLiveData<ProfileInfo>()
    val failureLiveData = MutableLiveData<String>()

    fun loadRepositoriesLogin(repositoryLogin: String) {
        viewModelScope.launch {
            try {
                val getInfoAboutDetails =
                    GitHubReposApplication.gitHubService.getInfoUser(repositoryLogin)
                profileInfoLiveData.value = getInfoAboutDetails
            } catch (e: Exception) {
                failureLiveData.value = e.message
            }
        }
    }

    fun onFavoritesAuthorsClick() {
        viewModelScope.launch {
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
    }
}
