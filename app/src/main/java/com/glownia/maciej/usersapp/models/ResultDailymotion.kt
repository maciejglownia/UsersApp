package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * Represents data we get from API
 * @param id represents user's id
 * @param screenName represents user's screenname
 *
 * List of parameters below will be saved into database after API call as a list.
 * @see [UsersDailymotion]
 */
@Parcelize
data class ResultDailymotion(
    @SerializedName("id") val id: String,
    @SerializedName("screenname") val screenName: String,
) : Parcelable