package com.glownia.maciej.usersapp.models

import com.google.gson.annotations.SerializedName

data class UsersDailymotion(
    @SerializedName("data")
    val results: List<ResultDailymotion>,
)