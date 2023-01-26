package com.glownia.maciej.usersapp.models.usergithubdetails


import com.google.gson.annotations.SerializedName

data class UserGithubDetails(
    @SerializedName("avatar_url")
    val avatarUrl: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("company")
    val company: String,
    @SerializedName("created_at")
    val createdAt: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("location")
    val location: String,
    @SerializedName("login")
    val login: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)