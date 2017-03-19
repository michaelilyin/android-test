package ru.michaelilyin.mobileapplication.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by micha on 19.03.2017.
 */
class ApiModule {

    private val retrofit: Retrofit

    val demoApi: DemoApi

    init {
        retrofit = Retrofit.Builder()
                .baseUrl("http://michaelilyin.ru")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        demoApi = retrofit.create(DemoApi::class.java)
    }
}