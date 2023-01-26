package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.utils.Constants.Companion.USERS_DAILYMOTION_TABLE
import com.google.gson.annotations.SerializedName

@Entity(tableName = USERS_DAILYMOTION_TABLE)
class UsersDailymotionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "screenname") val screenName: String? = null,
)