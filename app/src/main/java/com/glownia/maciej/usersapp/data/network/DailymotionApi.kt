package com.glownia.maciej.usersapp.data.network

import com.glownia.maciej.usersapp.models.UsersDailymotion
import retrofit2.Response
import retrofit2.http.GET

interface DailymotionApi {

    @GET("/users")
    suspend fun getUsersDailymotion(): Response<UsersDailymotion>

}