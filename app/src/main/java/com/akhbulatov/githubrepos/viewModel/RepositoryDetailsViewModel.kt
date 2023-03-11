package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.RepositoryDetails
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsViewModel : ViewModel() {
    lateinit var getRepositoriesDetails: Call<RepositoryDetails>
    val repositoryDetailsLiveData = MutableLiveData<RepositoryDetails>()
    val failureLiveData = MutableLiveData<String>()


    fun loadRepositoriesID(repositoryID: Int) {
        getRepositoriesDetails =
            GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryID)

        // Выполняем запрос на сервер
        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            // Вызывается когда с сервера приходит ответ после запроса
            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
                val repositoryDetails = response.body()
                repositoryDetailsLiveData.value = repositoryDetails
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {
                failureLiveData.value = t.message!!
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getRepositoriesDetails.cancel()
    }

}