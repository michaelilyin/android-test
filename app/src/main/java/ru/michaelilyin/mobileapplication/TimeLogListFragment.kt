package ru.michaelilyin.mobileapplication

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import ru.michaelilyin.mobileapplication.api.ApiServiceConnection
import ru.michaelilyin.mobileapplication.api.command.FetchTimeLogsCmd
import ru.michaelilyin.mobileapplication.api.model.TimeLog
import ru.michaelilyin.mobileapplication.service.ContextHandler

/**
 * Created by micha on 02.04.2017.
 */
class TimeLogListFragment(private val service: ApiServiceConnection) : Fragment() {

    private val recyclerView: RecyclerView
            by lazy { view.findViewById(R.id.time_log_list) as RecyclerView }

    private val swipeRefreshLayout: SwipeRefreshLayout
            by lazy {view.findViewById(R.id.refresh_time_log) as SwipeRefreshLayout }

    private val dataAdapter: DemoViewAdapter
            by lazy { DemoViewAdapter() }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.time_log_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layoutManager = LinearLayoutManager(this.activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = dataAdapter

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        swipeRefreshLayout.setOnRefreshListener { fetchData() }
    }

    override fun onStart() {
        super.onStart()

        swipeRefreshLayout.isRefreshing = true
        fetchData()
    }

    private fun fetchData() {
        Log.d(tag, "Try to send request")
        val handler = ContextHandler(this) { act, msg ->
            @Suppress("UNCHECKED_CAST")
            val result = msg.data.getSerializable("result") as ArrayList<TimeLog>
            act.dataAdapter.dataset = result
            act.dataAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
        }
        service.execute(FetchTimeLogsCmd(2), handler)
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        super.onPrepareOptionsMenu(menu)
    }
}