package com.akhbulatov.githubrepos

import android.content.Intent
import com.akhbulatov.githubrepos.fragments.*
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.github.terrakok.cicerone.androidx.ActivityScreen
import com.github.terrakok.cicerone.androidx.FragmentScreen

object Screens {
    fun repositoriesFr() = FragmentScreen { RepositoriesFragment() }
    fun repositoriesDetails(repository: Repository) =
        FragmentScreen { RepositoryDetailsFragment.createFragment(repository) }

    fun featuredAuthors() = FragmentScreen { FeaturedAuthorsFragment() }
    fun aboutApp() = FragmentScreen { AboutAppFragment() }
    fun settings() = FragmentScreen { SettingsFragment() }
    fun profileInfo(repositoryDetails: RepositoryDetails?) =
        FragmentScreen { ProfileInfoFragment.createFragment(repositoryDetails) }

    // Внешние экраны
    fun share(link: String) = ActivityScreen {
        val intent = Intent()
        intent.setAction(Intent.ACTION_SEND)
        intent.setType("text/plain")
        intent.putExtra(Intent.EXTRA_TEXT,link)
        intent
    }
}