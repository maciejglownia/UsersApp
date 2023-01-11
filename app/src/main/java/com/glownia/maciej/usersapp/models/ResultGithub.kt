package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents data we get from API
 * @param username represents user's login
 * @param avatar represents user's avatar_url displays user's photo
 * @param url represents user's source url where more user's details can be find
 *
 * List of parameters below will be saved into database after API call as a list.
 * @see [UsersGithub]
 */
@Parcelize
data class ResultGithub(
    @SerializedName("login") val username: String,
    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("url") val url: String,
) : Parcelable
