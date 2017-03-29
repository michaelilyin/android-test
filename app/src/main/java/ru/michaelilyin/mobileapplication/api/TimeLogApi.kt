package ru.michaelilyin.mobileapplication.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import ru.michaelilyin.mobileapplication.api.model.TimeLog

/**
 * Created by micha on 25.03.2017.
 */
interface TimeLogApi {
    @GET("/api/users/{id}/logs")
    fun getUserTimeLogs(@Path("id") id: Long): Call<List<TimeLog>>
}