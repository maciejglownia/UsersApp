package com.glownia.maciej.usersapp.models

import com.google.gson.annotations.SerializedName

data class UsersDailymotion(
    @SerializedName("list")
    val results: List<ResultDailymotion>,
)