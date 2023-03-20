package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao
import kotlinx.coroutines.launch

class FeaturedAuthorsViewModel: ViewModel() {
    val authorsListLiveData = MutableLiveData<List<FavoritesAuthors>>()

    val authorsDao: FavoritesAuthorsDao = GitHubReposApplication.appDatabase.authorsDao()


    fun onDelete(authors: FavoritesAuthors){
        viewModelScope.launch {
            authorsDao.deleteAuthors(authors = authors)
            loadAuthors()
        }
    }

    fun loadAuthors() {
        viewModelScope.launch {
            val authorsList: List<FavoritesAuthors> = authorsDao.getAllAuthors()
            authorsListLiveData.value = authorsList
        }
    }

    fun afterTextChanged(s:String) {
        viewModelScope.launch {
            val list = authorsDao.getAllAuthors()

            val listAuthors =
                list.filter { author: FavoritesAuthors -> author.fullName.contains(s, true) }
            authorsListLiveData.value = listAuthors
        }
    }
}