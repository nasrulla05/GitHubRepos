package com.akhbulatov.githubrepos.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.githubrepos.GitHubReposApplication
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.adapters.FavoritesAuthorsAdapter
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.akhbulatov.githubrepos.network.FavoritesAuthorsDao

class FeaturedAuthorsFragment : Fragment(R.layout.fragment_featured_authors) {

    val authorsDao: FavoritesAuthorsDao = GitHubReposApplication.appDatabase.authorsDao()
    val list = authorsDao.getAllAuthors()


    val authorsAdapter = FavoritesAuthorsAdapter(
        itemAuthorsListener = object : FavoritesAuthorsAdapter.AuthorsListener {
            override fun onDelete(authors: FavoritesAuthors) {
                authorsDao.deleteAuthors(authors)
                updateAuthorsList()
            }
        }
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar: Toolbar = view.findViewById(R.id.close_screen)
        toolbar.setNavigationOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                requireActivity().supportFragmentManager.popBackStack()
            }
        })

        val authorsRecyclerView: RecyclerView = view.findViewById(R.id.authors_recycleView)
        authorsRecyclerView.setAdapter(authorsAdapter)

        val dividerAuthors = DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
        authorsRecyclerView.addItemDecoration(dividerAuthors)

        updateAuthorsList()

        val authorSearch: EditText = view.findViewById(R.id.author_search)
        authorSearch.addTextChangedListener(object : TextWatcher {

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
                val listAuthors =
                    list.filter { author: FavoritesAuthors -> author.fullName.contains(s, true) }
                authorsAdapter.authors = listAuthors
                authorsAdapter.notifyDataSetChanged()
            }

            override fun afterTextChanged(s: Editable?) {
                // Вызывается ПОСЛЕ изменения текста
            }
        })
    }

    private fun updateAuthorsList() {
        val authorsList: List<FavoritesAuthors> = authorsDao.getAllAuthors()
        authorsAdapter.authors = authorsList
        authorsAdapter.notifyDataSetChanged()
    }


}