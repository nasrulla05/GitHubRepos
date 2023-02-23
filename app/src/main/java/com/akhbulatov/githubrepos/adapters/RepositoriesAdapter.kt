package com.akhbulatov.githubrepos.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.akhbulatov.githubrepos.R
import com.akhbulatov.githubrepos.databinding.ItemRepositoryBinding
import com.akhbulatov.githubrepos.models.Repository

class RepositoryAdapter(val repositoryListener: RepositoryListener) :
    RecyclerView.Adapter<RepositoryAdapter.RepositoriesViewHolder>() {

    var repositoriesList: MutableList<Repository> = mutableListOf()

    class RepositoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding : ItemRepositoryBinding = ItemRepositoryBinding.bind(itemView)
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

        holder.binding.name.setText(repository.name)
        holder.binding.login.setText(repository.owner.login)
        holder.binding.description.setText(repository.descriptor)

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