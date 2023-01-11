package com.glownia.maciej.usersapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glownia.maciej.usersapp.data.database.entities.UsersDailymotionEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersGithub(usersGithubEntity: UsersGithubEntity)

    @Query("SELECT * FROM users_github_table ORDER BY id ASC")
    fun readUsersGithub(): Flow<List<UsersGithubEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersDailymotion(usersDailymotionEntity: UsersDailymotionEntity)

    @Query("SELECT * FROM users_dailymotion_table ORDER BY id ASC")
    fun readUsersDailymotion(): Flow<List<UsersDailymotionEntity>>
}
