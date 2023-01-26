package com.glownia.maciej.usersapp.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.glownia.maciej.usersapp.R
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import com.glownia.maciej.usersapp.ui.fragments.UsersListFragmentDirections

class UsersRowBinding {

    companion object {
        @BindingAdapter("onUserClickListener")
        @JvmStatic
        fun onUserClickListener(singleRow: ConstraintLayout, users: UsersGithubEntity) {
            Log.d("onUserClickListener", "CALLED")
            singleRow.setOnClickListener {
                try {
                    val action = UsersListFragmentDirections
                        .actionUsersListFragmentToUserDetailsFragment(users)
                    singleRow.findNavController().navigate(action)
                } catch (e: Exception) {
                    Log.d("onUserClickListener", e.toString())
                }
            }
        }

        @BindingAdapter("readImageFromDatabase")
        @JvmStatic
        fun readImageFromDatabase(imageView: ImageView, usersGithubEntity: UsersGithubEntity) {
            imageView.load(usersGithubEntity.avatarUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}