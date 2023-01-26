package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents data we get from API
 *
 * List of parameters below will be saved into database after API call as a list.
 * @see [UsersGithub]
 */
@Parcelize
data class ResultGithub(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("login")
    val login: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
) : Parcelable
