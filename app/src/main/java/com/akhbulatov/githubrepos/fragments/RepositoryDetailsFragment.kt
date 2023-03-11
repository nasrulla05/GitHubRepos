package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.Screens
import com.akhbulatov.githubrepos.databinding.FragmentRepositoryDetailsBinding
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.akhbulatov.githubrepos.viewModel.RepositoryDetailsViewModel
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {
    var binding: FragmentRepositoryDetailsBinding? = null
    val viewModel: RepositoryDetailsViewModel by lazy {
        ViewModelProvider(this).get(RepositoryDetailsViewModel::class.java)
    }

    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoryDetailsBinding.bind(view)

        binding!!.toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.toolbar.setOnMenuItemClickListener(object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.share == item.itemId) {
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

        val repositoryID: Int = requireArguments().getInt(ARGUMENT_ID, 0)
        viewModel.repositoryDetailsLiveData.observe(viewLifecycleOwner) { t ->
            repositoryDetails = t

            Glide.with(this@RepositoryDetailsFragment)
                .load(t!!.owner.avatar)
                .into(binding!!.avatar)

            binding!!.toolbar.setTitle(t.name)

            binding!!.name.setText(t.name)

            binding!!.login.setText(t.owner.login)

            binding!!.description.setText(t.description)

            binding!!.link.setText(t.link)

            binding!!.star.setText(t.star)

            binding!!.reposts.setText(t.reposts)

            binding!!.error.setText(t.error)
        }
        viewModel.failureLiveData.observe(viewLifecycleOwner){t->
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                t,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.loadRepositoriesID(repositoryID = repositoryID)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {// Переменные и функции внутри вызываются БЕЗ создания объекта

        const val ARGUMENT_ID = "id"
        fun createFragment(repository: Repository): Fragment {
            val fragmentDetails = RepositoryDetailsFragment()
            val bundle = Bundle()
            bundle.putInt(ARGUMENT_ID, repository.id)
            fragmentDetails.arguments = bundle

            return fragmentDetails
        }
    }
}




