package com.akhbulatov.githubrepos.viewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoriesViewModel:ViewModel() {
    var getRepositoryCall: Call<List<Repository>> =
        GitHubReposApplication.gitHubService.getRepositories()

    val progressBarLiveData = MutableLiveData<Boolean>()
    val errorLiveData = MutableLiveData<Throwable>()
    val repositoriesLiveData = MutableLiveData<List<Repository>>()
    val repositoriesEditor = MutableLiveData<String>()


    fun loadRepositories(){
        progressBarLiveData.value = true

        getRepositoryCall.enqueue(object :Callback<List<Repository>>{
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                progressBarLiveData.value = false
                if(response.isSuccessful){
                    val repositoriesList:List<Repository>? = response.body()
                    // Получить кол-во отображаемых репозиториев из настроек
                    repositoriesLiveData.value = repositoriesList
                }
            }
            val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
                "git_hub_preferences",
                Context.MODE_PRIVATE
            )

            val repositoriesEditor:String?= sharedPreferences.getString("repositories",null)


            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                progressBarLiveData.value = false
                errorLiveData.value = t

            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        getRepositoryCall.cancel()
    }

}

