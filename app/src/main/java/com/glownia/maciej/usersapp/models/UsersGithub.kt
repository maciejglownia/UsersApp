package com.glownia.maciej.usersapp.models

import com.google.gson.annotations.SerializedName

data class UsersGithub(
    @SerializedName("list")
    val resultsUsersGithub: List<ResultGithub>
)