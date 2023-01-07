package com.glownia.maciej.usersapp.data

import com.glownia.maciej.usersapp.data.network.UsersApi
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.models.UsersGithub
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val usersApi: UsersApi,
) {
    suspend fun getUsersGithub(): Response<UsersGithub> {
        return usersApi.getUsersGithub()
    }

    suspend fun getUsersDailymotion(): Response<UsersDailymotion> {
        return usersApi.getUserDailymotion()
    }
}