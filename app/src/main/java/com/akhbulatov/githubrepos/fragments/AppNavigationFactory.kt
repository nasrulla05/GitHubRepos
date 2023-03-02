package com.akhbulatov.githubrepos.fragments

import com.akhbulatov.githubrepos.*
import me.aartikov.alligator.navigationfactories.RegistryNavigationFactory

class AppNavigationFactory: RegistryNavigationFactory() {
    init {
        registerFragment(AboutAppScreen::class.java,AboutAppFragment::class.java)
        registerFragment(SettingsScreen::class.java,SettingsFragment::class.java)
        registerFragment(FeaturedAuthorsScreen::class.java,FeaturedAuthorsFragment::class.java)
        registerFragment(RepositoryDetailsScreen::class.java,RepositoryDetailsFragment::class.java)
        registerFragment(RepositoriesScreen::class.java,RepositoriesFragment::class.java)
        registerFragment(ProfileInfoScreen::class.java,ProfileInfoFragment::class.java)
    }
}