package com.glownia.maciej.usersapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResultDailymotion(
    @SerializedName("id") val id: String,
    @SerializedName("screenname") val screenName: String,
) : Parcelable