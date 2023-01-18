package com.glownia.maciej.usersapp.data.database

import androidx.room.TypeConverter
import com.glownia.maciej.usersapp.models.ResultGithub
import com.glownia.maciej.usersapp.models.UsersDailymotion
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UsersTypeConverter {

    var gson = Gson()

    // Serialization
    @TypeConverter
    fun usersGithubToString(usersGithub: List<ResultGithub>): String {
        return gson.toJson(usersGithub)
    }

    // Deserialization  -> more: https://www.baeldung.com/kotlin/gson-parse-arrays
    @TypeConverter
    fun stringToUsersGithub(data: String): List<ResultGithub> {
        val listType = object : TypeToken<List<ResultGithub>>() {}.type
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