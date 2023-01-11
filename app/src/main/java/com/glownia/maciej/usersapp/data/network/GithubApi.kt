package com.glownia.maciej.usersapp.data.network

import com.glownia.maciej.usersapp.models.UsersGithub
import retrofit2.Response
import retrofit2.http.GET

interface GithubApi {

    @GET("/users")
    suspend fun getUsersGithub(): Response<List<UsersGithub>>

}