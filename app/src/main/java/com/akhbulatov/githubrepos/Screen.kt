package com.akhbulatov.githubrepos

import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import me.aartikov.alligator.Screen

class AboutAppScreen : Screen
class SettingsScreen:Screen
class FeaturedAuthorsScreen:Screen
class RepositoryDetailsScreen(val repositories:Repository):Screen
class RepositoriesScreen:Screen
class ProfileInfoScreen(val repositoryDetails: RepositoryDetails?):Screen