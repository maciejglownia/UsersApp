package com.glownia.maciej.usersapp.bindingadapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.glownia.maciej.usersapp.R
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity

class UsersRowBinding {

    companion object {

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