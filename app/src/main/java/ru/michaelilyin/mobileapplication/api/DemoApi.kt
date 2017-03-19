package ru.michaelilyin.mobileapplication.api

import retrofit2.Call
import retrofit2.http.GET
import ru.michaelilyin.mobileapplication.R
import ru.michaelilyin.mobileapplication.api.model.Demo

/**
 * Created by micha on 19.03.2017.
 */
interface DemoApi {
    @GET("/api/demo")
    fun getDemo(): Call<List<Demo>>
}