package com.glownia.maciej.usersapp.bindingadapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.glownia.maciej.usersapp.data.database.entities.UsersDailymotionEntity
import com.glownia.maciej.usersapp.models.UsersGithub
import com.glownia.maciej.usersapp.utils.NetworkResult

class ErrorsBinding {
    companion object {

        @BindingAdapter("readApiResponse","readDatabase",requireAll = true)
        @JvmStatic
        fun handleReadDataErrors(
            view: View,
            apiResponse: NetworkResult<UsersGithub>?,
            database: List<UsersDailymotionEntity>?
        ){
            when (view){
                is ImageView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                }
                is TextView ->{
                    view.isVisible = apiResponse is NetworkResult.Error && database.isNullOrEmpty()
                    view.text = apiResponse?.message.toString()
                }
            }
        }
    }
}