package ru.michaelilyin.mobileapplication.api

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.util.Log
import ru.michaelilyin.mobileapplication.service.command.Command
import java.io.Serializable
import java.util.concurrent.LinkedBlockingQueue

/**
 * Created by micha on 23.03.2017.
 */
class ApiServiceConnection : ServiceConnection {

    private val tag = ApiServiceConnection::class.java.simpleName

    private var service: ApiService.ApiServiceBinder? = null
    private val queue = LinkedBlockingQueue<Pair<Command<out Serializable>, Handler?>>()

    override fun onServiceDisconnected(name: ComponentName?) {
        Log.d(tag, "Service disconnected")
        this.service = null
    }

    override fun onServiceConnected(name: ComponentName?, service: IBinder) {
        Log.d(tag, "Service connected")
        val connectedService = service as ApiService.ApiServiceBinder
        Log.d(tag, "Execute pending commands")
        while (queue.size != 0) {
            val pair = queue.poll()
            connectedService.execute(pair.first, pair.second)
            Log.v(tag, "Commands left: ${queue.size}")
        }
        this.service = connectedService
    }

    fun execute(command: Command<out Serializable>, handler: Handler? = null) {
        service?.execute(command, handler) ?: appendToQueue(command, handler)
    }

    fun appendToQueue(command: Command<out Serializable>, handler: Handler?) {
        Log.d(tag, "Service does not bound yet")
        queue.add(Pair(command, handler))
    }
}