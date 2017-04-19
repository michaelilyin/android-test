package ru.michaelilyin.mobileapplication.api

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import ru.michaelilyin.mobileapplication.api.command.ApiException
import ru.michaelilyin.mobileapplication.service.command.Command
import java.io.Serializable
import java.util.concurrent.Executors

/**
 * Created by micha on 19.03.2017.
 */
class ApiService : Service() {

    private val tag = ApiService::class.java.simpleName

    private val executor = Executors.newSingleThreadExecutor()
    private val binder = ApiServiceBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    override fun onDestroy() {
        if (!executor.isShutdown) {
            executor.shutdown()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        return Service.START_STICKY
    }

    private fun <T : Serializable> execute(command: Command<T>, handler: Handler? = null) {
        executor.submit {
            try {
                Log.d(tag, "Execute command: $command")
                val result = command.call()
                if (Log.isLoggable(tag, Log.DEBUG)) {
                    Log.d(tag, "Received data: $result")
                }
                val message = Message.obtain()
                message.data.putSerializable("result", result)
                handler?.post {
                    try {
                        Log.d(tag, "Execute callback in thread ${Thread.currentThread().name}")
                        handler.dispatchMessage(message)
                        Log.d(tag, "Callback completed")
                    } catch (e: Exception) {
                        Log.e(tag, "Callback exception: ${e.message}", e)
                    }
                }
            } catch (e: ApiException) {
                Log.e(tag, "Api exception: ${e.message}", e)
            } catch (e: Exception) {
                Log.e(tag, "Execution error: ${e.message}", e)
            }
        }
    }

    inner class ApiServiceBinder: Binder() {
        fun <T : Serializable> execute(command: Command<T>, handler: Handler? = null) {
            this@ApiService.execute(command, handler)
        }
    }

}