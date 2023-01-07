package com.glownia.maciej.usersapp.data.network

import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.models.UsersGithub
import retrofit2.Response
import retrofit2.http.GET

interface UsersApi {

    @GET("/users")
    suspend fun getUsersGithub(): Response<UsersGithub>

    @GET("/users")
    suspend fun getUserDailymotion(): Response<UsersDailymotion>

}