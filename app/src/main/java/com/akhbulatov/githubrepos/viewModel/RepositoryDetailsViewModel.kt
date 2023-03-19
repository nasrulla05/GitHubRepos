package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.RepositoryDetails
import kotlinx.coroutines.launch

class RepositoryDetailsViewModel : ViewModel() {
    val repositoryDetailsLiveData = MutableLiveData<RepositoryDetails>()
    val failureLiveData = MutableLiveData<String>()


    fun loadRepositoriesID(repositoryID: Int) {
        viewModelScope.launch {
            try {
                val repositoryDetails =
                    GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryID)
                repositoryDetailsLiveData.value = repositoryDetails
            } catch (e: Exception) {
                failureLiveData.value = e.message
            }
        }
    }
}



