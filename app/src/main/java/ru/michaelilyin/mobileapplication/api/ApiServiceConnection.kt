package ru.michaelilyin.mobileapplication.api

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.util.Log
import ru.michaelilyin.mobileapplication.service.command.Command
import java.io.Serializable

/**
 * Created by micha on 23.03.2017.
 */
class ApiServiceConnection : ServiceConnection {

    private val tag = ApiServiceConnection::class.java.simpleName

    private var service: ApiService.ApiServiceBinder? = null

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(tag, "Service disconnected")
        this.service = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        Log.d(tag, "Service connected")
        this.service = service as ApiService.ApiServiceBinder
    }

    fun <T : Serializable> execute(command: Command<T>, handler: Handler? = null) {
        service?.execute(command, handler) ?: Log.d(tag, "Service is not bound")
    }
}