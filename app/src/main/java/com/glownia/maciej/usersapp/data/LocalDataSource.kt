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

    /** users_github_table */
    suspend fun insertUsersGithub(usersGithubEntity: UsersGithubEntity) {
        usersDao.insertUsersGithub(usersGithubEntity)
    }

    fun readUsersGithub(): Flow<List<UsersGithubEntity>> {
        return usersDao.readUsersGithub()
    }

    /** user_github_details_table */
    suspend fun insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity: UserGithubDetailsEntity) {
        usersDao.insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity)
    }

    fun readUserGithubByLogin(login: String): Flow<UserGithubDetailsEntity> {
        return usersDao.readUserGithubByLogin(login)
    }

    /** users_dailymotion_table */
    suspend fun insertUsersDailymotion(usersDailymotionEntity: UsersDailymotionEntity) {
        usersDao.insertUsersDailymotion(usersDailymotionEntity)
    }

    fun readUsersDailymotion(): Flow<List<UsersDailymotionEntity>> {
        return usersDao.readUsersDailymotion()
    }
}