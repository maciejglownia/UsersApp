package com.glownia.maciej.usersapp.data

import com.glownia.maciej.usersapp.data.network.DailymotionApi
import com.glownia.maciej.usersapp.data.network.GithubApi
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.models.usergithubdetails.UserGithubDetails
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val githubApi: GithubApi,
    private val dailymotionApi: DailymotionApi,
) {
    suspend fun getUsersGithub(): Response<List<ResultGithub>> {
        return githubApi.getUsersGithub()
    }

    suspend fun getUsersDailymotion(): Response<UsersDailymotion> {
        return dailymotionApi.getUsersDailymotion()
    }

    suspend fun getUserGithubDetails(login: String) : Response<UserGithubDetails> {
        return githubApi.getUserGithubDetails(login)
    }
}