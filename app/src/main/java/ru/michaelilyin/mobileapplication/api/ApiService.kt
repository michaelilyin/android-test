package ru.michaelilyin.mobileapplication.api

import android.app.Service
import android.content.Intent
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

    private val tag = ApiService::class.java.name

    private val executor = Executors.newSingleThreadExecutor()

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        if (!executor.isShutdown) {
            executor.shutdown()
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        @Suppress("UNCHECKED_CAST")
        val command = intent.getSerializableExtra("command") as Command<out Serializable>
        executor.submit {
            try {
                val result = command.call()
                if (Log.isLoggable(tag, Log.DEBUG)) {
                    Log.d(tag, "Received data: $result")
                }
                val message = Message.obtain()
                message.data.putSerializable("result", result)
//                        handler?.post {
//                            Log.d(tag, "Execute callback in thread ${Thread.currentThread().name}")
//                            handler.dispatchMessage(message)
//                        }
            } catch (e: ApiException) {
                Log.e(tag, "Api exception: ${e.message}")
            } catch (e: Exception) {
                Log.e(tag, "Execution error", e)
            }
        }
        return Service.START_STICKY
    }
}