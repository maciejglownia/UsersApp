package com.glownia.maciej.usersapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.utils.Constants.Companion.USERS_DAILYMOTION_TABLE

@Entity(tableName = USERS_DAILYMOTION_TABLE)
class UsersDailymotionEntity(
    var usersDailymotion: UsersDailymotion,
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}