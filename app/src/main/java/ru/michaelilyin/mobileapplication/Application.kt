package ru.michaelilyin.mobileapplication

import ru.michaelilyin.mobileapplication.api.ApiModule

/**
 * Created by micha on 19.03.2017.
 */
class Application : android.app.Application() {

    companion object {
        private lateinit var _apiModule: ApiModule
         val apiModule: ApiModule
            get() = _apiModule
    }

    override fun onCreate() {
        super.onCreate()
        _apiModule = ApiModule(
                resources.getString(R.string.api_host),
                resources.getString(R.string.api_port),
                resources.getString(R.string.api_root)
        )
    }
}