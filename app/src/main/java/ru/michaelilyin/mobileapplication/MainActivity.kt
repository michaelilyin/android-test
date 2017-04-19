package ru.michaelilyin.mobileapplication

import android.app.Activity
import android.app.FragmentManager
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
import ru.michaelilyin.mobileapplication.service.ContextHandler
import android.support.v7.widget.DividerItemDecoration

class MainActivity : Activity() {

    private val tag = MainActivity::class.java.name

    private val service = ApiServiceConnection()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindService(Intent(this, ApiService::class.java), service, Context.BIND_AUTO_CREATE)

        val transaction = fragmentManager.beginTransaction()
        val timeLogListFragment = TimeLogListFragment(service)
        transaction.replace(R.id.activity_main, timeLogListFragment)
        transaction.commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        unbindService(service)
    }

}
