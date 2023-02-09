package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoFragment :
    Fragment(R.layout.fragment_profile_info) {

    lateinit var getInfoAboutDetails: Call<ProfileInfo>
    var responseInfo: ProfileInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.arrow_back_toolbar)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })
        toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.favorites == item.itemId) {
                    val authors = FavoritesAuthors(
                        responseInfo!!.avatarAva,
                        responseInfo!!.fullName,
                        responseInfo!!.login
                    )
                    val authorsDao: FavoritesAuthorsDao =
                        GitHubReposApplication.appDatabase.authorsDao()
                    authorsDao.insertAuthors(authors)

                    val snackbar : Snackbar = Snackbar.make(
                        view,
                        "Добавлен в Избранные авторы",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
                return true
            }
        })

        val repositoryLogin: String = requireArguments().getString("login", "")
        getInfoAboutDetails =
            GitHubReposApplication.gitHubService.getInfoUser(repositoryLogin)

        getInfoAboutDetails.enqueue(object : Callback<ProfileInfo> {
            override fun onResponse(
                call: Call<ProfileInfo>,
                response: Response<ProfileInfo>
            ) {
                responseInfo = response.body()

                if (responseInfo != null) {
                    val avatar: ImageView = view.findViewById(R.id.avatar_60)
                    Glide.with(this@ProfileInfoFragment)
                        .load(responseInfo!!.avatarAva)
                        .into(avatar)

                    toolbar.setTitle(responseInfo!!.login)

                    val name: TextView = view.findViewById(R.id.full_name)
                    name.setText(responseInfo!!.fullName)

                    val login: TextView = view.findViewById(R.id.login)
                    login.setText(responseInfo!!.login)

                    val bio : TextView = view.findViewById(R.id.bio)
                    bio.setText(responseInfo!!.bio)

                    val location: TextView = view.findViewById(R.id.location)
                    location.setText(responseInfo!!.location)

                    val email: TextView = view.findViewById(R.id.gmail)
                    email.setText(responseInfo!!.email)

                    val followers: TextView = view.findViewById(R.id.followers)
                    followers.setText("Подписчики: ${responseInfo!!.followers}")

                    val following: TextView = view.findViewById(R.id.following)
                    following.setText("Подписки: ${responseInfo!!.following}")

                    val publicRepos: TextView = view.findViewById(R.id.public_repos)
                    publicRepos.setText(responseInfo!!.public_repos.toString())

                }
            }

            override fun onFailure(call: Call<ProfileInfo>, t: Throwable) {
                val snackbar: Snackbar = Snackbar.make(
                    requireView(),
                    t.message!!,
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
        })
    }
}