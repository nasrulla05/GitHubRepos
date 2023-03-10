package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.Screens
import com.akhbulatov.githubrepos.databinding.FragmentRepositoryDetailsBinding
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    var binding : FragmentRepositoryDetailsBinding? = null

    lateinit var getRepositoriesDetails: Call<RepositoryDetails>
    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailsBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.toolbar.setOnMenuItemClickListener(object:Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.share==item.itemId){
                    GitHubReposApplication.router.navigateTo(Screens.share(repositoryDetails!!.link))
                }
                return true
            }
        })

        binding!!.linearLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                GitHubReposApplication.router.navigateTo(Screens.profileInfo(repositoryDetails))
            }
        })

        val repositoryId: Int = requireArguments().getInt(ARGUMENT_ID, 0)
        getRepositoriesDetails =
            GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryId)

        // ?????????????????? ???????????? ???? ????????????
        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            // ???????????????????? ?????????? ?? ?????????????? ???????????????? ?????????? ?????????? ??????????????
            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
               repositoryDetails = response.body()
                if (repositoryDetails != null) {

                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.owner.avatar)
                        .into(binding!!.avatar)

                    binding!!.toolbar.setTitle(repositoryDetails!!.name)

                    binding!!.name.setText(repositoryDetails!!.name)

                    binding!!.login.setText(repositoryDetails!!.owner.login)

                    binding!!.description.setText(repositoryDetails!!.description)

                    binding!!.link.setText(repositoryDetails!!.link)

                    binding!!.star.setText(repositoryDetails!!.star)

                    binding!!.reposts.setText(repositoryDetails!!.reposts)

                    binding!!.error.setText(repositoryDetails!!.error)
                }
            }

            override fun onFailure(call: Call<RepositoryDetails>, t: Throwable) {
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

    companion object{// ???????????????????? ?? ?????????????? ???????????? ???????????????????? ?????? ???????????????? ??????????????

        const val ARGUMENT_ID = "id"
        fun createFragment(repository: Repository): Fragment{
            val fragmentDetails = RepositoryDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(ARGUMENT_ID, repository.id)
            fragmentDetails.arguments = bundle

            return fragmentDetails
        }
    }
}




