package com.glownia.maciej.usersapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.glownia.maciej.usersapp.data.database.entities.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsersGithub(usersEntity: UsersEntity)

    @Query("SELECT * FROM users_table ORDER BY id ASC")
    fun readUsersGithub(): Flow<List<UsersEntity>>

}
