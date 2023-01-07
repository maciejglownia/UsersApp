package com.glownia.maciej.usersapp.data

import com.glownia.maciej.usersapp.data.database.UsersDao
import com.glownia.maciej.usersapp.data.database.entities.UsersEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val usersDao: UsersDao
) {

    suspend fun insertUsersGithub(usersEntity: UsersEntity) {
        usersDao.insertUsersGithub(usersEntity)
    }

    fun readUsersGithub(): Flow<List<UsersEntity>> {
        return usersDao.readUsersGithub()
    }

    // TODO : User Dailymotion as above
}