package com.glownia.maciej.usersapp.data.database

import androidx.room.TypeConverter
import com.glownia.maciej.usersapp.models.UsersGithub
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class UsersTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun usersGithubToString(usersGithub: UsersGithub): String {
        return gson.toJson(usersGithub)
    }

    @TypeConverter
    fun stringToUsersGithub(data: String): UsersGithub {
        val listType = object : TypeToken<UsersGithub>() {}.type
        return gson.fromJson(data, listType)
    }
}