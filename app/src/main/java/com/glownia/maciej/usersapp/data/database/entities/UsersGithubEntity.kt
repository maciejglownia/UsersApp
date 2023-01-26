package com.glownia.maciej.usersapp.data.database.entities

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.utils.Constants.Companion.USERS_GITHUB_TABLE
import kotlinx.parcelize.Parcelize

@Entity(tableName = USERS_GITHUB_TABLE)
@Parcelize
class UsersGithubEntity(
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String?,
    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    val id: Int?,
    @ColumnInfo(name = "login")
    val login: String?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "url")
    val url: String?,
) : Parcelable