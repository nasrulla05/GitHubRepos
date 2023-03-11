package com.akhbulatov.githubrepos

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.akhbulatov.githubrepos.database.AppDatabase
import com.akhbulatov.githubrepos.network.GitHubService
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GitHubReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        context = this.applicationContext

        appDatabase = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "github_database"
        )
            .allowMainThreadQueries()
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        gitHubService = retrofit.create()

        cicerone = Cicerone.create()
        router = cicerone.router
        navigateHolder = cicerone.getNavigatorHolder()
    }

    companion object {
        lateinit var context:Context
        lateinit var appDatabase: AppDatabase
        lateinit var gitHubService: GitHubService

        private lateinit var cicerone: Cicerone<Router>
        lateinit var router: Router
        lateinit var navigateHolder: NavigatorHolder
    }
}