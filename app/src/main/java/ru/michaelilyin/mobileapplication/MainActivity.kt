package ru.michaelilyin.mobileapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import ru.michaelilyin.mobileapplication.api.ApiService
import ru.michaelilyin.mobileapplication.api.command.FetchDemoCmd
import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.service.AbstractBackgroundService

class MainActivity : Activity() {

    private val tag = MainActivity::class.java.name

    private val inputProcessor = InputProcessor()
    private val serviceConnection = ApiService.Connection()

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.elements) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)

        val btnFetch = findViewById(R.id.btn_fetch) as Button

        val linearManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearManager

        recyclerView.adapter = DemoViewAdapter()
    }

    override fun onResume() {
        super.onResume()
        if (!serviceConnection.isBound) {
            Log.d(tag, "Try to bind service")
            val intent = Intent(this@MainActivity, ApiService::class.java)
            bindService(intent, serviceConnection, BIND_AUTO_CREATE)
        }
        Log.d(tag, "Try to send request")
        val handler = AbstractBackgroundService.Handler(this@MainActivity) { act, msg ->
            @Suppress("UNCHECKED_CAST")
            val result = msg.data.getSerializable("result") as ArrayList<Demo>
            Log.d(tag, "$result")
            act.recyclerView.adapter = DemoViewAdapter(result)
        }
        serviceConnection.execute(FetchDemoCmd(), handler)
    }

    override fun onPause() {
        super.onPause()
        if (serviceConnection.isBound) {
            unbindService(serviceConnection)
        }
    }

    private inner class InputProcessor : View.OnClickListener {

        override fun onClick(v: View?) {
            if (v == null) return
            when (v.id) {

            }
        }
    }

}
