package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.models.UsersGithub
import com.glownia.maciej.usersapp.utils.Constants.Companion.USERS_GITHUB_TABLE

@Entity(tableName = USERS_GITHUB_TABLE)
class UsersGithubEntity(
    var usersGithub: List<UsersGithub>,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}