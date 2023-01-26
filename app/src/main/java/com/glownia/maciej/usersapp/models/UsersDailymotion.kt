package com.glownia.maciej.usersapp.models

import com.google.gson.annotations.SerializedName

/**
 * Represents list of users from:
 * [Daily Motion](https://api.dailymotion.com/users)
 *
 * @see [ResultDailymotion] to know what exactly data we get.
 */
data class UsersDailymotion(
    @SerializedName("list")
    val resultsUserDailymotion: List<ResultDailymotion>,
)