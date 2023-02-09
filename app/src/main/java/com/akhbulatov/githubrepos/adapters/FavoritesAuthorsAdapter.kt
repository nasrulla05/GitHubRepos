package com.akhbulatov.githubrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.models.FavoritesAuthors
import com.bumptech.glide.Glide

class FavoritesAuthorsAdapter(val itemAuthorsListener: AuthorsListener) :
    RecyclerView.Adapter<FavoritesAuthorsAdapter.AuthorsViewHolder>() {

    var authors: List<FavoritesAuthors> = emptyList()

    class AuthorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.favorite_avatar)
        val fullName: TextView = itemView.findViewById(R.id.favorites_full_name)
        val electedLogin: TextView = itemView.findViewById(R.id.elected_login)
        val delete: ImageView = itemView.findViewById(R.id.delete)
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
            .into(holder.avatar)

        holder.fullName.setText(authors.fullName)
        holder.electedLogin.setText(authors.favoritesLogin)

        holder.delete.setOnClickListener(object : View.OnClickListener {
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