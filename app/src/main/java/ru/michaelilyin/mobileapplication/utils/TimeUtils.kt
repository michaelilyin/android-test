package ru.michaelilyin.mobileapplication.utils

import android.content.Context
import android.text.format.DateUtils
import java.util.*

/**
 * Created by micha on 18.04.2017.
 */
fun formatDateTime(context: Context, date: Long): CharSequence {
    return DateUtils.getRelativeDateTimeString(
            context,
            date,
            DateUtils.MINUTE_IN_MILLIS,
            DateUtils.WEEK_IN_MILLIS,
            DateUtils.FORMAT_ABBREV_ALL
    )
}