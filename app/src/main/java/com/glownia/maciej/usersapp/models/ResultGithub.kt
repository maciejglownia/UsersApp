package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import androidx.room.PrimaryKey
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
    @SerializedName("login") var username: String? = null,
    @SerializedName("avatar_url") var avatar: String? = null,
    @SerializedName("url") var url: String? = null,
) : Parcelable
