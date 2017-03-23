package ru.michaelilyin.mobileapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Menu
import android.view.View
import ru.michaelilyin.mobileapplication.api.ApiService
import ru.michaelilyin.mobileapplication.api.command.FetchDemoCmd

class MainActivity : Activity() {

    private val tag = MainActivity::class.java.name

    private val inputProcessor = InputProcessor()

    private val recyclerView: RecyclerView by lazy {
        findViewById(R.id.elements) as RecyclerView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView.setHasFixedSize(true)

        val linearManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearManager

        recyclerView.adapter = DemoViewAdapter()
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu?): Boolean {
        return super.onCreatePanelMenu(featureId, menu)
    }

    private inner class InputProcessor : View.OnClickListener {

        override fun onClick(v: View?) {
            if (v == null) return
            when (v.id) {

            }
        }

        private fun onFetchButtonClicked() {
            Log.d(tag, "Try to send request")
//            val handler = ActivityHandler(this@MainActivity) { act, msg ->
//                @Suppress("UNCHECKED_CAST")
//                val result = msg.data.getSerializable("result") as ArrayList<Demo>
//                Log.d(tag, "$result")
//                act.recyclerView.adapter = DemoViewAdapter(result)
//            }
            val intent = Intent(this@MainActivity, ApiService::class.java)
                    .putExtra("command", FetchDemoCmd())
            startService(intent)
        }

    }

}
