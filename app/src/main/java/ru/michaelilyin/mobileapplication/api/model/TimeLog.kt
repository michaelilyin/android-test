package ru.michaelilyin.mobileapplication.api.model

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by micha on 25.03.2017.
 */
class TimeLog(
        var id: Long = 0,
        var userId: Long = 0,
        var userName: String = "",
        var actionId: Long = 0,
        var actionName: String = "",
        var timeBegin: Date = TimeLog.defaultTimeBegin,
        var timeEnd: Date? = null,
        var comment: String? = null
) {
    companion object {
        private val defaultTimeBegin = SimpleDateFormat().parse("01/01/2016 4:5 PM, PDT")
    }
}