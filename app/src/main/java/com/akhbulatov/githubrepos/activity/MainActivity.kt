package com.akhbulatov.githubrepos.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.RepositoriesScreen
import me.aartikov.alligator.NavigationContext

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    // Вызывается при создании Activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigateRepositoriesFragment()

        Log.d("MainActivity", "Был вызван onCreate")
    }

    // Вызывается при уничтожении экрана
    override fun onDestroy() {
        super.onDestroy()
        Log.d("MainActivity", "был вызван onDestroy")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MainActivity", "был вызван onStart")
    }

    //Вызывается, когда экран становится невидимым
    override fun onStop() {
        super.onStop()
        Log.d("MainActivity", "Был вызван onStop")
    }

    private fun navigateRepositoriesFragment() {
        // Выполняем переход на экран списка репозиториев
        GitHubReposApplication.navigator.replace(RepositoriesScreen())
    }

    override fun onResume() {
        super.onResume()
        val navigatorContext : NavigationContext =
            NavigationContext.Builder(this,GitHubReposApplication.navigatorFactory)
                .fragmentNavigation(supportFragmentManager,R.id.fragment)
                .build()
        GitHubReposApplication.navigatorContextBinder.bind(navigatorContext)
    }

    override fun onPause() {
        super.onPause()
        GitHubReposApplication.navigatorContextBinder.unbind(this)
    }

    override fun onBackPressed() {
        GitHubReposApplication.navigator.goBack()
    }
}