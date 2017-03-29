package ru.michaelilyin.mobileapplication

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import ru.michaelilyin.mobileapplication.api.ApiService
import ru.michaelilyin.mobileapplication.api.ApiServiceConnection
import ru.michaelilyin.mobileapplication.api.command.FetchDemoCmd
import ru.michaelilyin.mobileapplication.api.command.FetchTimeLogsCmd
import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.api.model.TimeLog
import ru.michaelilyin.mobileapplication.service.ActivityHandler

class MainActivity : Activity() {

    private val tag = MainActivity::class.java.name

    private val service = ApiServiceConnection()
    private val inputProcessor = InputProcessor()

    private val recyclerView: RecyclerView by lazy { findViewById(R.id.elements) as RecyclerView }
    private val dataAdapter: DemoViewAdapter by lazy { DemoViewAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(Intent(this, ApiService::class.java), service, Context.BIND_AUTO_CREATE)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dataAdapter
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu?): Boolean {
        return super.onCreatePanelMenu(featureId, menu)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(service)
    }

    private fun fetchData() {
        Log.d(tag, "Try to send request")
        val handler = ActivityHandler(this) { act, msg ->
            @Suppress("UNCHECKED_CAST")
            val result = msg.data.getSerializable("result") as ArrayList<TimeLog>
            act.dataAdapter.dataset = result
            act.dataAdapter.notifyDataSetChanged()
        }
        service.execute(FetchTimeLogsCmd(2), handler)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.btn_refresh -> {
                fetchData()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
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
