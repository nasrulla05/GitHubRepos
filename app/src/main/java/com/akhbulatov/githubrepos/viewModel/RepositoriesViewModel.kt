package com.akhbulatov.githubrepos.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.Repository
import kotlinx.coroutines.launch

class RepositoriesViewModel : ViewModel() {
    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Throwable>()
    val repositoriesLiveData = MutableLiveData<List<Repository>>()
    val repositoriesEditorLiveData = MutableLiveData<String>()


    fun loadRepositories() {
        viewModelScope.launch {
            try {
                progressBarLiveData.value = true
                progressBarLiveData.value = false
                val repositoriesList: List<Repository> =
                    GitHubReposApplication.gitHubService.getRepositories()
                // Получить кол-во отображаемых репозиториев из настроек
                repositoriesLiveData.value = repositoriesList

                val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
                    "git_hub_preferences",
                    Context.MODE_PRIVATE
                )

                val repositoriesEditor: String? = sharedPreferences.getString("repositories", null)
                repositoriesEditorLiveData.value = repositoriesEditor
            } catch (e: Exception) {
                progressBarLiveData.value = false
                errorLiveData.value = e
            }
        }
    }
}


