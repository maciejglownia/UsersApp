package com.glownia.maciej.usersapp.data

import com.glownia.maciej.usersapp.data.network.DailymotionApi
import com.glownia.maciej.usersapp.data.network.GithubApi
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.UsersDailymotion
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
}