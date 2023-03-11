package com.akhbulatov.githubrepos.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao

class FeaturedAuthorsViewModel: ViewModel() {
    val authorsListLiveData = MutableLiveData<List<FavoritesAuthors>>()

    val authorsDao: FavoritesAuthorsDao = GitHubReposApplication.appDatabase.authorsDao()
    val list = authorsDao.getAllAuthors()


    fun onDelete(authors: FavoritesAuthors){
        authorsDao.deleteAuthors(authors = authors)
        val authorsList: List<FavoritesAuthors> = authorsDao.getAllAuthors()
        authorsListLiveData.value = authorsList
    }

    fun loadAuthors(){
        val authorsList: List<FavoritesAuthors> = authorsDao.getAllAuthors()
        authorsListLiveData.value = authorsList

    }


}