package ru.michaelilyin.mobileapplication

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import ru.michaelilyin.mobileapplication.api.ApiService
import ru.michaelilyin.mobileapplication.api.command.FetchDemoCmd
import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.service.AbstractBackgroundService
import ru.michaelilyin.mobileapplication.service.command.Command
import ru.michaelilyin.mobileapplication.service.command.CountDownCmd
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : Activity() {

    private val tag = MainActivity::class.java.name

    private val inputProcessor = InputProcessor()
    private val serviceConnection = ApiService.Connection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnStart = findViewById(R.id.btn_start) as Button
        val btnStop = findViewById(R.id.btn_stop) as Button
        val btnFetch = findViewById(R.id.btn_fetch) as Button

        btnStart.setOnClickListener(inputProcessor)
        btnStop.setOnClickListener(inputProcessor)
        btnFetch.setOnClickListener(inputProcessor)
    }

    override fun onResume() {
        super.onResume()
//        bindService(Intent(this, AbstractBackgroundService::class.java), serviceConnection, BIND_AUTO_CREATE)
    }

    override fun onPause() {
        super.onPause()
//        unbindService(serviceConnection)
    }

    private inner class InputProcessor : View.OnClickListener {

        private fun onButtonStartClick() {
            Log.d(tag, "Start service")
            if (!serviceConnection.isBound) {
                val intent = Intent(this@MainActivity, ApiService::class.java)
                bindService(intent, serviceConnection, BIND_AUTO_CREATE)
            }
        }

        private fun onButtonStopClick() {
            Log.d(tag, "Stop service")
            if (serviceConnection.isBound) {
                unbindService(serviceConnection)
            }
        }

        private fun onButtonFetchClick() {
            Log.d(tag, "Fetch data")
            serviceConnection.execute(FetchDemoCmd(),
                    AbstractBackgroundService.Handler(this@MainActivity) { act, msg ->
                        @Suppress("UNCHECKED_CAST")
                        val result = msg.data.getSerializable("result") as ArrayList<Demo>
                        Log.d(tag, "$result")
                    })
        }

        override fun onClick(v: View?) {
            if (v == null) return
            when (v.id) {
                R.id.btn_start -> onButtonStartClick()
                R.id.btn_stop -> onButtonStopClick()
                R.id.btn_fetch -> onButtonFetchClick()
            }
        }
    }

}
