package ru.michaelilyin.mobileapplication.service

import android.app.Activity
import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Parcel
import android.util.Log
import ru.michaelilyin.mobileapplication.service.command.Command
import java.io.Serializable
import java.lang.ref.WeakReference
import java.lang.reflect.Constructor
import java.util.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Created by micha on 12.03.2017.
 */
abstract class AbstractBackgroundService : Service() {

    private val tag = AbstractBackgroundService::class.java.name

    private val executor = Executors.newSingleThreadExecutor()
    private val binder = DataServiceBinder()

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onDestroy() {
        if (!executor.isShutdown) {
            executor.shutdown()
        }
    }

    inner class DataServiceBinder : android.os.Binder() {
        val tag = DataServiceBinder::class.java.name

        fun <R : Serializable> execute(command: Command<R>, handler: android.os.Handler? = null) {
            try {
                Log.d(tag, "Execute command from thread ${Thread.currentThread().name}")
                executor.submit {
                    Log.d(tag, "Execute command in thread ${Thread.currentThread().name}")
                    try {
                        val result = command.call()
                        val message = Message.obtain()
                        message.data.putSerializable("result", result)
                        handler?.post {
                            Log.d(tag, "Execute callback in thread ${Thread.currentThread().name}")
                            handler.dispatchMessage(message)
                        }
                    } catch (e: Exception) {
                        Log.e(tag, "Execution error", e)
                    }
                }
            } catch (e: TimeoutException) {
                Log.w(tag, "Command timeout")
            } catch (e: Exception) {
                Log.e(tag, "Execution error", e)
            }
        }

    }

    class Handler<T : Activity> constructor(
            activity: T,
            private val callback: ((T, Message) -> Unit)
    ) : android.os.Handler() {

        private val activity = WeakReference(activity)

        override fun handleMessage(msg: Message?) {
            val activity = activity.get()
            if (activity != null && msg != null) {
                callback(activity, msg)
            }
        }
    }
}