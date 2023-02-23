package com.akhbulatov.githubrepos.fragments

import android.content.Context
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.widget.Toolbar.OnMenuItemClickListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.adapters.RepositoryAdapter
import com.akhbulatov.githubrepos.databinding.FragmentRepositoriesBinding
import com.akhbulatov.githubrepos.models.Repository
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Экран списка репозиториев
class RepositoriesFragment : Fragment(R.layout.fragment_repositories) {
    var binding : FragmentRepositoriesBinding? = null

    val repositoryAdapter = RepositoryAdapter(
        repositoryListener = object : RepositoryAdapter.RepositoryListener {
            override fun repositoryClick(repository: Repository) {

                val fragmentDetails = RepositoryDetailsFragment()

                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment, fragmentDetails)
                transaction.addToBackStack(null)
                transaction.commit()

                val bundle = Bundle()
                bundle.putInt("id", repository.id)
                fragmentDetails.arguments = bundle

            }
        }
    )
    var getRepositoryCall: Call<List<Repository>> =
        GitHubReposApplication.gitHubService.getRepositories()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRepositoriesBinding.bind(view)

        binding!!.toolbar.setOnMenuItemClickListener(object : OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.featured_authors == item.itemId) {
                    val authors = FeaturedAuthorsFragment()

                    val transaction: FragmentTransaction =
                        requireActivity().supportFragmentManager.beginTransaction()
                    transaction.replace(R.id.fragment, authors)
                    transaction.addToBackStack(null)
                    transaction.commit()
                } else
                    if (R.id.about_the_application == item.itemId) {
                        val aboutApp = AboutAppFragment()

                        val transaction: FragmentTransaction =
                            requireActivity().supportFragmentManager.beginTransaction()
                        transaction.replace(R.id.fragment, aboutApp)
                        transaction.addToBackStack(null)
                        transaction.commit()
                    } else
                        if (R.id.settings == item.itemId) {
                            val settings = SettingsFragment()

                            val transaction: FragmentTransaction =
                                requireActivity().supportFragmentManager.beginTransaction()
                            transaction.replace(R.id.fragment, settings)
                            transaction.addToBackStack(null)
                            transaction.commit()
                        }
                return true
            }
        })

        val divider = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.recycleView.addItemDecoration(divider)

        binding!!.recycleView.adapter = repositoryAdapter
        loadRepositories()

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
    }


    private fun loadRepositories() {
        binding!!.progressBar.visibility = View.VISIBLE

        getRepositoryCall.clone().enqueue(object : Callback<List<Repository>> {
            override fun onResponse(
                call: Call<List<Repository>>,
                response: Response<List<Repository>>
            ) {
                binding!!.progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val repositoriesList: List<Repository>? = response.body()
                    // Получить кол-во отображаемых репозиториев из настроек

                    val sharedPreferences = requireContext().getSharedPreferences(
                        "git_hub_preferences",
                        Context.MODE_PRIVATE
                    )
                    val repositoriesEditor: String? =
                        sharedPreferences.getString("repositories", null)
                    val repositoriesInt = repositoriesEditor!!.toInt()

                    val takeList: List<Repository> = repositoriesList!!.take(repositoriesInt)
                    repositoryAdapter.repositoriesList = takeList!!.toMutableList()
                    repositoryAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Repository>>, t: Throwable) {
                binding!!.progressBar.visibility = View.GONE

                val snackbar: Snackbar = Snackbar.make(
                    requireView(),
                    t.message!!,
                    Snackbar.LENGTH_LONG
                )
                snackbar.show()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        getRepositoryCall.cancel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}