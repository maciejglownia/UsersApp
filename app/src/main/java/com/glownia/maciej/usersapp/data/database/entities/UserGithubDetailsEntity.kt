package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_github_details_table")
class UserGithubDetailsEntity(
    @ColumnInfo(name = "id")
    val id: Int? = null,
    @PrimaryKey
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "name")
    val name: String? = null,
    @ColumnInfo(name = "location")
    val location: String? = null,
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String? = null,
    @ColumnInfo(name = "blog")
    val blog: String? = null,
    @ColumnInfo(name = "company")
    val company: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: String? = null,
    @ColumnInfo(name = "type")
    val type: String? = null,
    @ColumnInfo(name = "url")
    val url: String? = null,
)