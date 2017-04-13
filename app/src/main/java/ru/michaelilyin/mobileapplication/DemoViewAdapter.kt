package ru.michaelilyin.mobileapplication

import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import ru.michaelilyin.mobileapplication.api.model.TimeLog

/**
 * Created by micha on 19.03.2017.
 */
class DemoViewAdapter(
        var dataset: List<TimeLog> = emptyList()
) : RecyclerView.Adapter<DemoViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val actionNameText = view.findViewById(R.id.time_log_action) as TextView
        val actionTimesText = view.findViewById(R.id.time_log_dates) as TextView

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.time_log_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val action = dataset[position].actionName
        val start = dataset[position].timeBegin
        val end = dataset[position].timeEnd
        holder.actionNameText.text = action
        val from = DateUtils.getRelativeDateTimeString(
                holder.view.context,
                start.time,
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL
        )
        val to = DateUtils.getRelativeDateTimeString(
                holder.view.context,
                end?.time ?: System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
                DateUtils.WEEK_IN_MILLIS,
                DateUtils.FORMAT_ABBREV_ALL
        )
        holder.actionTimesText.text = holder.view.context
                .getString(R.string.pattern_time_range, from, to)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

}