package com.akhbulatov.githubrepos

import com.akhbulatov.githubrepos.fragments.*
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
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

}