package ru.michaelilyin.mobileapplication.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by micha on 25.03.2017.
 */
class TimeLog(
        @SerializedName("id")
        @Expose
        var id: Long = 0,

        @SerializedName("userId")
        @Expose
        var userId: Long = 0,

        @SerializedName("userName")
        @Expose
        var userName: String = "",

        @SerializedName("actionId")
        @Expose
        var actionId: Long = 0,

        @SerializedName("actionName")
        @Expose
        var actionName: String = "",

        @SerializedName("timeBegin")
        @Expose
        var timeBegin: Date = TimeLog.defaultTimeBegin,

        @SerializedName("timeEnd")
        @Expose
        var timeEnd: Date? = null,

        @SerializedName("comment")
        @Expose
        var comment: String? = null
) {
    companion object {
        private val defaultTimeBegin = SimpleDateFormat().parse("01/01/2016 4:5 PM, PDT")
    }
}