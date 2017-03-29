package ru.michaelilyin.mobileapplication

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.api.model.TimeLog

/**
 * Created by micha on 19.03.2017.
 */
class DemoViewAdapter(
        var dataset: List<TimeLog> = emptyList()
) : RecyclerView.Adapter<DemoViewAdapter.ViewHolder>() {

    class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TextView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = dataset[position].actionName
        val user = dataset[position].userName
        val start = dataset[position].timeBegin.toString()
        holder.view.text = "$user:$action:$start"
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}