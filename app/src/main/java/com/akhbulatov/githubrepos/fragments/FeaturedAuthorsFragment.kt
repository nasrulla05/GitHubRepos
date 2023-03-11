package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.adapters.FavoritesAuthorsAdapter
import com.akhbulatov.githubrepos.databinding.FragmentFeaturedAuthorsBinding
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.viewModel.FeaturedAuthorsViewModel

class FeaturedAuthorsFragment : Fragment(R.layout.fragment_featured_authors) {
    val viewModel: FeaturedAuthorsViewModel by lazy {
        ViewModelProvider(this).get(FeaturedAuthorsViewModel::class.java)
    }
    var binding: FragmentFeaturedAuthorsBinding? = null


    val authorsAdapter = FavoritesAuthorsAdapter(
        itemAuthorsListener = object : FavoritesAuthorsAdapter.AuthorsListener {
            override fun onDelete(authors: FavoritesAuthors) {
                viewModel.onDelete(authors)
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFeaturedAuthorsBinding.bind(view)

        binding!!.closeScreen.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                GitHubReposApplication.router.exit()
            }
        })

        binding!!.authorsRecycleView.setAdapter(authorsAdapter)

        val dividerAuthors = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        binding!!.authorsRecycleView.addItemDecoration(dividerAuthors)


        binding!!.authorSearch.addTextChangedListener(object : TextWatcher {

            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) { // Вызывается ДО изменения текста
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) { // Вызывается ВО время изменения текста
//                val listAuthors =
//                    list.filter { author: FavoritesAuthors -> author.fullName.contains(s, true) }
//                authorsAdapter.authors = listAuthors
//                authorsAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
                // Вызывается ПОСЛЕ изменения текста
            }
        })

        viewModel.authorsListLiveData.observe(viewLifecycleOwner,object :Observer<List<FavoritesAuthors>>{
            override fun onChanged(t: List<FavoritesAuthors>) {
                authorsAdapter.authors = t
                authorsAdapter.notifyDataSetChanged()
            }
        })

        viewModel.loadAuthors()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }


}