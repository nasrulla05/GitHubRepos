package com.akhbulatov.githubrepos.fragments

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.models.RepositoryDetails
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RepositoryDetailsFragment : Fragment(R.layout.fragment_repository_details) {

    lateinit var getRepositoriesDetails: Call<RepositoryDetails>
    var repositoryDetails: RepositoryDetails? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.arrow_back)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        toolbar.setOnMenuItemClickListener(object:Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem): Boolean {
                if (R.id.share==item.itemId){
                    val intent = Intent()
                    intent.setAction(Intent.ACTION_SEND)
                    intent.setType("text/plain")
                    intent.putExtra(Intent.EXTRA_TEXT,repositoryDetails!!.link)
                    requireActivity().startActivity(intent)
                }
                return true
            }
        })

        val linearLayout: LinearLayout = view.findViewById(R.id.linear_layout)
        linearLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val profileInfoFragment = ProfileInfoFragment()

                val bundle = Bundle()
                bundle.putString("login",repositoryDetails!!.owner.login)
                profileInfoFragment.arguments = bundle

                val transaction: FragmentTransaction =
                    requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment, profileInfoFragment)
                transaction.addToBackStack(null)
                transaction.commit()

            }
        })

        val repositoryId: Int = requireArguments().getInt("id", 0)
        getRepositoriesDetails =
            GitHubReposApplication.gitHubService.getRepositoriesDetails(repositoryId)

        // Выполняем запрос на сервер
        getRepositoriesDetails.enqueue(object : Callback<RepositoryDetails> {
            // Вызывается когда с сервера приходит ответ после запроса
            override fun onResponse(
                call: Call<RepositoryDetails>,
                response: Response<RepositoryDetails>
            ) {
               repositoryDetails = response.body()
                if (repositoryDetails != null) {

                    val avatar: ImageView = view.findViewById(R.id.avatar)
                    Glide.with(this@RepositoryDetailsFragment)
                        .load(repositoryDetails!!.owner.avatar)
                        .into(avatar)

                    val toolbar: Toolbar = view.findViewById(R.id.arrow_back)
                    toolbar.setTitle(repositoryDetails!!.name)

                    val name: TextView = view.findViewById(R.id.name)
                    name.setText(repositoryDetails!!.name)

                    val login: TextView = view.findViewById(R.id.login)
                    login.setText(repositoryDetails!!.owner.login)

                    val description: TextView = view.findViewById(R.id.description)
                    description.setText(repositoryDetails!!.description)

                    val link: TextView = view.findViewById(R.id.link)
                    link.setText(repositoryDetails!!.link)

                    val star: TextView = view.findViewById(R.id.star)
                    star.setText(repositoryDetails!!.star)

                    val reposts: TextView = view.findViewById(R.id.reposts)
                    reposts.setText(repositoryDetails!!.reposts)

                    val error: TextView = view.findViewById(R.id.error)
                    error.setText(repositoryDetails!!.error)
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
}




