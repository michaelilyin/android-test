package ru.michaelilyin.mobileapplication.api.command

import ru.michaelilyin.mobileapplication.api.RestResourceCmd
import ru.michaelilyin.mobileapplication.api.model.TimeLog

/**
 * Created by micha on 25.03.2017.
 */
class FetchTimeLogsCmd(
        private val userId: Long
) : RestResourceCmd<ArrayList<TimeLog>>() {
    override fun call(): ArrayList<TimeLog> {
        val response = apiModule.timeLogApi.getUserTimeLogs(userId).execute()
        if (response.isSuccessful) {
            return response.body().toArrayList()
        } else {
            throw ApiException(response.code(), response.message())
        }
    }
}