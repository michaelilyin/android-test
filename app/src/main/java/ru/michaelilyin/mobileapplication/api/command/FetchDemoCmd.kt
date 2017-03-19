package ru.michaelilyin.mobileapplication.api.command

import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.api.RestResourceCmd

/**
 * Created by micha on 12.03.2017.
 */
class FetchDemoCmd : RestResourceCmd<ArrayList<Demo>>() {
    override fun call(): ArrayList<Demo> {
        val response = apiModule.demoApi.getDemo().execute()
        if (response.isSuccessful) {
            return response.body().toArrayList()
        } else {
            throw RuntimeException(response.message())
        }
    }
}