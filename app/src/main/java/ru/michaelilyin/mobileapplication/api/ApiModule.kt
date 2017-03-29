package ru.michaelilyin.mobileapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by micha on 19.03.2017.
 */
class ApiModule {

    private val retrofit: Retrofit

    val demoApi: DemoApi
    val timeLogApi: TimeLogApi

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("http://michaelilyin.ru:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        demoApi = retrofit.create(DemoApi::class.java)
        timeLogApi = retrofit.create(TimeLogApi::class.java)
    }
}