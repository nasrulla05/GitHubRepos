package com.akhbulatov.githubrepos.viewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication

class SettingsViewModel:ViewModel() {

    val repositoriesLiveData = MutableLiveData<String>()

    fun repositoriesEditor(){
        val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )
        val repositoriesEditor: String =
            sharedPreferences.getString("repositories", null)!!
        repositoriesLiveData.value = repositoriesEditor
    }

    fun saveSettingsClick(){
        val sharedPreferences = GitHubReposApplication.context.getSharedPreferences(
            "git_hub_preferences",
            Context.MODE_PRIVATE
        )
        val repositoriesList = repositoriesLiveData.value.toString()

        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString("repositories", repositoriesList)
        editor.apply()
    }

}