package com.glownia.maciej.usersapp.data

import com.glownia.maciej.usersapp.data.database.UsersDao
import com.glownia.maciej.usersapp.data.database.entities.UserGithubDetailsEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersDailymotionEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val usersDao: UsersDao
) {

    suspend fun insertUsersGithub(usersGithubEntity: UsersGithubEntity) {
        usersDao.insertUsersGithub(usersGithubEntity)
    }

    fun readUsersGithub(): Flow<List<UsersGithubEntity>> {
        return usersDao.readUsersGithub()
    }

    suspend fun insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity: UserGithubDetailsEntity) {
        usersDao.insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity)
    }

    suspend fun insertUsersDailymotion(usersDailymotionEntity: UsersDailymotionEntity) {
        usersDao.insertUsersDailymotion(usersDailymotionEntity)
    }

    fun readUsersDailymotion(): Flow<List<UsersDailymotionEntity>> {
        return usersDao.readUsersDailymotion()
    }
}