package com.glownia.maciej.usersapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.glownia.maciej.usersapp.databinding.SingleRowBinding
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.UsersGithub
import com.glownia.maciej.usersapp.utils.UsersDiffUtil

class UsersAdapter : RecyclerView.Adapter<UsersAdapter.MyViewHolder>() {

    private var users = emptyList<ResultGithub>()

    class MyViewHolder(private val binding: SingleRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: ResultGithub) {
            binding.result = result
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
    }

    override fun getItemCount(): Int {
        return users.size
    }

    fun setData(newData: UsersGithub) {
        val usersDiffUtil =
            UsersDiffUtil(users, newData.resultsUsersGithub)
        val diffUtilResult = DiffUtil.calculateDiff(usersDiffUtil)
        users = newData.resultsUsersGithub
        diffUtilResult.dispatchUpdatesTo(this)
    }
}