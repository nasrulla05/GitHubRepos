package com.akhbulatov.githubrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.models.Repository

class RepositoryAdapter(val repositoryListener: RepositoryListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoriesViewHolder>() {

    var repositoriesList: MutableList<Repository> = mutableListOf()

    class RepositoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name)
        val login: TextView = itemView.findViewById(R.id.login)
        val description: TextView = itemView.findViewById(R.id.description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val layoutInflater: LayoutInflater = LayoutInflater.from(parent.context)

        val itemView: View = layoutInflater.inflate(
            R.layout.item_repository,
            parent,
            false
        )
        return RepositoriesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val repository: Repository = repositoriesList.get(position)

        holder.name.setText(repository.name)
        holder.login.setText(repository.owner.login)
        holder.description.setText(repository.descriptor)

        holder.itemView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                repositoryListener.repositoryClick(repository)
            }
        })
    }

    override fun getItemCount(): Int {
        return repositoriesList.size
    }

    interface RepositoryListener {
        fun repositoryClick(repository: Repository)
    }

}