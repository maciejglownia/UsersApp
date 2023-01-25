package com.glownia.maciej.usersapp.bindingadapters

import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import coil.load
import com.glownia.maciej.usersapp.R
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.ui.fragments.UsersListFragmentDirections

class UsersRowBinding {

    companion object {
        @BindingAdapter("onUserClickListener")
        @JvmStatic
        fun onUserClickListener(singleRow: ConstraintLayout, users: ResultGithub) {
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

        @BindingAdapter("loadImageFromUrl")
        @JvmStatic
        fun loadImageFromUrl(imageView: ImageView, imageUrl: String) {
            imageView.load(imageUrl) {
                crossfade(600)
                error(R.drawable.ic_error_placeholder)
            }
        }
    }
}