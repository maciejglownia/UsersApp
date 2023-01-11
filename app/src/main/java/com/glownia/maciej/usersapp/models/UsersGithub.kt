package com.glownia.maciej.usersapp.models

/**
 * Represents list of users from:
 * [Github](https://api.github.com/users)
 *
 * @see [ResultGithub] to know what exactly data we get.
 */

class UsersGithub(
    val resultsUsersGithub: List<ResultGithub>
)