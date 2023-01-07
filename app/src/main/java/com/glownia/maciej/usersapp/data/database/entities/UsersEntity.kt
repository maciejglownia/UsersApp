package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.models.UsersGithub

@Entity(tableName = "users_table")
class UsersEntity(
    var usersGithub: UsersGithub
) {
    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}