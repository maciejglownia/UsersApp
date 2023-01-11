package com.glownia.maciej.usersapp.data.database

import androidx.room.TypeConverter
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.glownia.maciej.usersapp.models.UsersGithub
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UsersTypeConverter {

    var gson = Gson()

    // Serialization
    @TypeConverter
    fun usersGithubToString(usersGithub: List<UsersGithub>): String {
        return gson.toJson(usersGithub)
    }

    // Deserialization
    @TypeConverter
    fun stringToUsersGithub(data: String): List<UsersGithub> {
        val listType = object : TypeToken<List<UsersGithub>>() {}.type
        return gson.fromJson(data, listType)
    }

    // Serialization
    @TypeConverter
    fun usersDailymotionToString(usersDailymotion: UsersDailymotion): String {
        return gson.toJson(usersDailymotion)
    }

    // Deserialization
    @TypeConverter
    fun stringToUsersDailymotion(data: String): UsersDailymotion {
        val listType = object : TypeToken<UsersDailymotion>() {}.type
        return gson.fromJson(data, listType)
    }
}