package com.glownia.maciej.usersapp.data.network

import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.usergithubdetails.UserGithubDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("/users")
    suspend fun getUsersGithub(): Response<List<ResultGithub>>

    @GET("/users/{login}")
    suspend fun getUserGithubDetails(@Path("login") login: String): Response<UserGithubDetails>
}