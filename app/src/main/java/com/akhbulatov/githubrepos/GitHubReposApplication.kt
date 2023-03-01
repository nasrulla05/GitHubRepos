package com.akhbulatov.githubrepos

import android.app.Application
import androidx.room.Room
import com.akhbulatov.githubrepos.database.AppDatabase
import com.akhbulatov.githubrepos.fragments.AppNavigationFactory
import com.akhbulatov.githubrepos.network.GitHubService
import me.aartikov.alligator.AndroidNavigator
import me.aartikov.alligator.NavigationContextBinder
import me.aartikov.alligator.Navigator
import me.aartikov.alligator.ScreenResolver
import me.aartikov.alligator.navigationfactories.NavigationFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class GitHubReposApplication : Application() {
    override fun onCreate() {
        super.onCreate()
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

        androidNavigator = AndroidNavigator(AppNavigationFactory())
        navigatorFactory = androidNavigator.navigationFactory
        navigatorContextBinder = androidNavigator
        navigator = androidNavigator
        screenResolver = androidNavigator.screenResolver
    }

    companion object {
        lateinit var appDatabase: AppDatabase
        lateinit var gitHubService: GitHubService

        private lateinit var androidNavigator :AndroidNavigator
        lateinit var navigatorFactory :NavigationFactory
        lateinit var navigatorContextBinder :NavigationContextBinder
        lateinit var navigator :Navigator
        lateinit var screenResolver :ScreenResolver
    }
}