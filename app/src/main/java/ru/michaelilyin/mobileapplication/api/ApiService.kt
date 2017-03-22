package ru.michaelilyin.mobileapplication.api

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import ru.michaelilyin.mobileapplication.service.AbstractBackgroundService
import ru.michaelilyin.mobileapplication.service.command.Command
import java.io.Serializable

/**
 * Created by micha on 19.03.2017.
 */
class ApiService : AbstractBackgroundService() {
    class Connection : ServiceConnection {

        private val tag = Connection::class.java.name

        private var service: AbstractBackgroundService.DataServiceBinder? = null
        private var connected: Boolean = false

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(tag, "Data service unbound")
            connected = false
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            if (service != null) {
                this.service = service as AbstractBackgroundService.DataServiceBinder
                connected = true
            }
            Log.d(tag, "Data service bound")
        }

        val isBound: Boolean
            get() = connected && service != null

        fun <R : Serializable> execute(command: Command<R>, handler: android.os.Handler? = null) {
            if (isBound) {
                service?.execute(command, handler) ?: Log.w(tag, "Service does not bound")
            } else {
                Log.d(tag, "Service not bound yet")
            }
        }
    }
}