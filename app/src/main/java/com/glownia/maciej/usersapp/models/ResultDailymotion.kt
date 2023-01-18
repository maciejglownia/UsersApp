package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import androidx.room.PrimaryKey
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
    @PrimaryKey
    @SerializedName("id") var id: String? = null,
    @SerializedName("screenname") var screenName: String? = null,
) : Parcelable