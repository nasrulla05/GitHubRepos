package com.akhbulatov.githubrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.ItemFeaturedAuthorsBinding
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.bumptech.glide.Glide

class FavoritesAuthorsAdapter(val itemAuthorsListener: AuthorsListener) :
    RecyclerView.Adapter<FavoritesAuthorsAdapter.AuthorsViewHolder>() {

    var authors: List<FavoritesAuthors> = emptyList()

    class AuthorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemFeaturedAuthorsBinding = ItemFeaturedAuthorsBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)
        val itemView: View = layoutInflater.inflate(R.layout.item_featured_authors, parent, false)
        return AuthorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        val authors: FavoritesAuthors = authors.get(position)

        Glide.with(holder.itemView)
            .load(authors.avatar)
            .into(holder.binding.favoriteAvatar)

        holder.binding.favoritesFullName.setText(authors.fullName)
        holder.binding.electedLogin.setText(authors.favoritesLogin)

        holder.binding.delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                itemAuthorsListener.onDelete(authors)
            }
        })
    }

    override fun getItemCount(): Int {
        return authors.size
    }

    interface AuthorsListener {
        fun onDelete(authors: FavoritesAuthors)

    }
}