package com.akhbulatov.githubrepos.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication

class SettingsViewModel:ViewModel() {

    val repositoriesLiveData = MutableLiveData<String>()

    val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
        "git_hub_preferences",
        Context.MODE_PRIVATE
    )

    fun loadRepositoriesEditor(){

        val repositoriesNumber: String =
            sharedPreferences.getString("repositories", null)!!
        repositoriesLiveData.value = repositoriesNumber
    }

    fun saveSettingsClick(){

        val repositoriesList = repositoriesLiveData.value.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoriesList)
        editor.apply()
    }

}