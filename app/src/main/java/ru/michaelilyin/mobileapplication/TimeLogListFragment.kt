package ru.michaelilyin.mobileapplication

import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import ru.michaelilyin.mobileapplication.api.ApiServiceConnection
import ru.michaelilyin.mobileapplication.api.command.FetchTimeLogsCmd
import ru.michaelilyin.mobileapplication.api.model.TimeLog
import ru.michaelilyin.mobileapplication.service.ContextHandler
import ru.michaelilyin.mobileapplication.utils.resettableLazy
import ru.michaelilyin.mobileapplication.utils.resettableManager

/**
 * Created by micha on 02.04.2017.
 */
class TimeLogListFragment(private val service: ApiServiceConnection) : Fragment() {

    private val lazyMgr = resettableManager()

    private val recyclerView: RecyclerView
            by resettableLazy(lazyMgr) { view.findViewById(R.id.time_log_list) as RecyclerView }

    private val swipeRefreshLayout: SwipeRefreshLayout
            by resettableLazy(lazyMgr) {view.findViewById(R.id.refresh_time_log) as SwipeRefreshLayout }

    private val dataAdapter: DemoViewAdapter
            by resettableLazy(lazyMgr) { DemoViewAdapter(fragmentManager) }

    private val _tag = javaClass.simpleName;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.time_log_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(_tag, "onActivityCreated")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(_tag, "onViewCreated")
        recyclerView.adapter = dataAdapter
        val layoutManager = LinearLayoutManager(this.activity)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(recyclerView.context,
                layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        swipeRefreshLayout.setOnRefreshListener {
            Log.d(_tag, "onRefresh")
            fetchData()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        swipeRefreshLayout.setOnRefreshListener(null)
        lazyMgr.reset()
    }

    override fun onStart() {
        super.onStart()
        Log.d(_tag, "onStart")

        swipeRefreshLayout.isRefreshing = true
        fetchData()
    }

    private fun fetchData() {
        Log.d(_tag, "Try to send request")
        val handler = ContextHandler(this) { act, msg ->
            Log.d(_tag, "Process response")
            @Suppress("UNCHECKED_CAST")
            val result = msg.data.getSerializable("result") as ArrayList<TimeLog>
            act.dataAdapter.dataset = result
            act.dataAdapter.notifyDataSetChanged()
            swipeRefreshLayout.isRefreshing = false
            Log.d(_tag, "Response processed")
        }
        service.execute(FetchTimeLogsCmd(2), handler)
    }
}