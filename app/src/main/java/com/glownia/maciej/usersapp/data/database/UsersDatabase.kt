package com.glownia.maciej.usersapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.glownia.maciej.usersapp.data.database.entities.UsersEntity

@Database(
    entities = [UsersEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(UsersTypeConverter::class)
abstract class UsersDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao
}