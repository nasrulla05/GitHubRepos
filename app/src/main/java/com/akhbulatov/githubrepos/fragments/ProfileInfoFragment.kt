package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.FragmentProfileInfoBinding
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileInfoFragment :
    Fragment(R.layout.fragment_profile_info) {
    var binding : FragmentProfileInfoBinding? = null

    lateinit var getInfoAboutDetails: Call<ProfileInfo>
    var responseInfo: ProfileInfo? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileInfoBinding.bind(view)

        binding!!.arrowBackToolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })
        binding!!.arrowBackToolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
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
                        "???????????????? ?? ?????????????????? ????????????",
                        Snackbar.LENGTH_LONG
                    )
                    snackbar.show()
                }
                return true
            }
        })

        val repositoryLogin: String = requireArguments().getString(ARGUMENT_LOGIN, "")
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

                    binding!!.arrowBackToolbar.setTitle(responseInfo!!.login)

                    binding!!.fullName.setText(responseInfo!!.fullName)

                    binding!!.login.setText(responseInfo!!.login)

                    binding!!.bio.setText(responseInfo!!.bio)

                    binding!!.location.setText(responseInfo!!.location)

                    binding!!.gmail.setText(responseInfo!!.email)

                    binding!!.followers.setText("????????????????????: ${responseInfo!!.followers}")

                    binding!!.following.setText("????????????????: ${responseInfo!!.following}")

                    binding!!.publicRepos.setText(responseInfo!!.public_repos.toString())

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

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object{
        const val ARGUMENT_LOGIN = "login"

        fun createFragment(repositoryDetails: RepositoryDetails?): Fragment{
            val profileInfoFragment = ProfileInfoFragment()
            val bundle = Bundle()
            bundle.putString(ARGUMENT_LOGIN,repositoryDetails!!.owner.login)
            profileInfoFragment.arguments = bundle

            return profileInfoFragment
        }
    }
}