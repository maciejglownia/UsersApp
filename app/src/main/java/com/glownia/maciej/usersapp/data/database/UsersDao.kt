package com.glownia.maciej.usersapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glownia.maciej.usersapp.data.database.entities.UserGithubDetailsEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersDailymotionEntity
import com.glownia.maciej.usersapp.data.database.entities.UsersGithubEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    /** users_github_table */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersGithub(usersGithubEntity: UsersGithubEntity)

    @Query("SELECT * FROM users_github_table ORDER BY id ASC")
    fun readUsersGithub(): Flow<List<UsersGithubEntity>>

    /** user_github_details_table */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserGithubDetailsOfChosenUser(userGithubDetailsEntity: UserGithubDetailsEntity)

    @Query("SELECT * FROM user_github_details_table WHERE login= :login")
    fun readUserGithubByLogin(login: String): Flow<UserGithubDetailsEntity>

    /** users_dailymotion_table */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersDailymotion(usersDailymotionEntity: UsersDailymotionEntity)

    @Query("SELECT * FROM users_dailymotion_table ORDER BY id ASC")
    fun readUsersDailymotion(): Flow<List<UsersDailymotionEntity>>
}
