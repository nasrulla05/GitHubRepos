package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.FragmentProfileInfoBinding
import com.akhbulatov.githubrepos.models.ProfileInfo
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.akhbulatov.githubrepos.viewModel.ProfileInfoViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class ProfileInfoFragment :
    Fragment(R.layout.fragment_profile_info) {
    var binding : FragmentProfileInfoBinding? = null
    val viewModel:ProfileInfoViewModel by lazy {
        ViewModelProvider(this).get(ProfileInfoViewModel::class.java)
    }
    var profileInfo: ProfileInfo? = null

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
                  viewModel.onFavoritesAuthorsClick()

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

        val repositoryLogin: String = requireArguments().getString(ARGUMENT_LOGIN, "")
        viewModel.profileInfoLiveData.observe(viewLifecycleOwner) { t ->
            profileInfo = t
            Glide.with(this@ProfileInfoFragment)
                .load(profileInfo!!.avatarAva)
                .into(binding!!.avatar60)

            binding!!.arrowBackToolbar.setTitle(t.login)

            binding!!.fullName.setText(t.fullName)

            binding!!.login.setText(t.login)

            binding!!.bio.setText(t.bio)

            binding!!.location.setText(t.location)

            binding!!.gmail.setText(t.email)

            binding!!.followers.setText("Подписчики: ${t.followers}")

            binding!!.following.setText("Подписки: ${t.following}")

            binding!!.publicRepos.setText(t.public_repos.toString())
        }

        viewModel.failureLiveData.observe(viewLifecycleOwner){t->
            val error:Snackbar = Snackbar.make(
                requireView(),
                t,
                Snackbar.LENGTH_LONG
            )
            error.show()
        }

        viewModel.loadRepositoriesLogin(repositoryLogin)
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