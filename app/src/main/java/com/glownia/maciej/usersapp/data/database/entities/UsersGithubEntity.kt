package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.utils.Constants.Companion.USERS_GITHUB_TABLE

@Entity(tableName = USERS_GITHUB_TABLE)
class UsersGithubEntity(
    var usersGithub: List<ResultGithub>,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}