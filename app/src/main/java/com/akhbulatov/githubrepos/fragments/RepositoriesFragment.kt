package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.Screens
import com.akhbulatov.githubrepos.adapters.RepositoryAdapter
import com.akhbulatov.githubrepos.databinding.FragmentRepositoriesBinding
import com.akhbulatov.githubrepos.models.Repository
import com.akhbulatov.githubrepos.viewModel.RepositoriesViewModel
import com.google.android.material.snackbar.Snackbar

// Экран списка репозиториев
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {
    var binding: FragmentRepositoriesBinding? = null
    val viewModel: RepositoriesViewModel by lazy {
        ViewModelProvider(this).get(RepositoriesViewModel::class.java)
    }

    val repositoryAdapter = RepositoryAdapter(
        repositoryListener = object : RepositoryAdapter.RepositoryListener {
            override fun repositoryClick(repository: Repository) {

                GitHubReposApplication.router.navigateTo(Screens.repositoriesDetails(repository))
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesBinding.bind(view)

        binding!!.toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.featured_authors == item.itemId) {
                    GitHubReposApplication.router.navigateTo(Screens.featuredAuthors())
                } else
                    if (R.id.about_the_application == item.itemId) {

                        GitHubReposApplication.router.navigateTo(Screens.aboutApp())
                    } else
                        if (R.id.settings == item.itemId) {

                            GitHubReposApplication.router.navigateTo(Screens.settings())
                        }
                return true
            }
        })

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.recycleView.addItemDecoration(divider)

        binding!!.recycleView.adapter = repositoryAdapter

        binding!!.spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> repositoryAdapter.repositoriesList.sortBy { it.id }
                    1 -> repositoryAdapter.repositoriesList.sortBy { it.name }
                }
                repositoryAdapter.notifyDataSetChanged()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        })

        viewModel.repositoriesLiveData.observe(
            viewLifecycleOwner
        ) { t ->
            repositoryAdapter.repositoriesList = t!!.toMutableList()
            repositoryAdapter.notifyDataSetChanged()
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) { t ->
            if (t) {
                binding!!.progressBar.visibility = View.VISIBLE
            } else {
                binding!!.progressBar.visibility = View.GONE
            }
        }

        viewModel.errorLiveData.observe(viewLifecycleOwner) { t ->
            val snackbar: Snackbar = Snackbar.make(
                requireView(),
                t.message!!,
                Snackbar.LENGTH_LONG
            )
            snackbar.show()
        }
        viewModel.loadRepositories()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}
