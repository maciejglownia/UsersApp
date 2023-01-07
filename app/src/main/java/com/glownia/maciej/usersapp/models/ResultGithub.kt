package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultGithub(
    @SerializedName("id") val username: String,
//    @SerializedName("avatar_url") val avatar: String,
    @SerializedName("screenname") val url: String,
) : Parcelable