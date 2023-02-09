package com.glownia.maciej.usersapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import com.glownia.maciej.usersapp.databinding.SingleRowBinding
import com.glownia.maciej.usersapp.utils.UsersDiffUtil

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private var users = emptyList<UsersGithubEntity>()

    class MyViewHolder(val binding: SingleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(usersGithubEntity: UsersGithubEntity) {
            binding.usersGithubEntity = usersGithubEntity
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = SingleRowBinding.inflate(layoutInflater, parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentUser = users[position]
        holder.bind(currentUser)

        holder.binding.rowCardView.setOnClickListener {
            onItemClickListener?.let { it(currentUser) }
        }
    }

    private var onItemClickListener: ((UsersGithubEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (UsersGithubEntity) -> Unit) {
        onItemClickListener = listener
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setData(newData: List<UsersGithubEntity>) {
        val usersDiffUtil =
            UsersDiffUtil(users, newData)
        val diffUtilResult = DiffUtil.calculateDiff(usersDiffUtil)
        users = newData
        diffUtilResult.dispatchUpdatesTo(this)
    }
}