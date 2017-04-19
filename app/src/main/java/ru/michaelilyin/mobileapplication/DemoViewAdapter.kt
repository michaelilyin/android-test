package ru.michaelilyin.mobileapplication

import android.app.FragmentManager
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import ru.michaelilyin.mobileapplication.api.model.TimeLog
import ru.michaelilyin.mobileapplication.utils.ImageMap
import ru.michaelilyin.mobileapplication.utils.formatDateTime
import java.util.*

/**
 * Created by micha on 19.03.2017.
 */
class DemoViewAdapter(
        private val fragmentManager: FragmentManager,
        var dataset: List<TimeLog> = emptyList()
) : RecyclerView.Adapter<DemoViewAdapter.ViewHolder>() {

    companion object {
        private val NORMAL = 1
        private val UNCOMPLETED = 2
    }

    private val inputProcessor = InputProcessor()

    open inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val actionNameText = view.findViewById(R.id.time_log_action) as TextView
        val actionTimesText = view.findViewById(R.id.time_log_dates) as TextView
        val actionImage = view.findViewById(R.id.time_log_action_icon) as ImageView

        init {
            view.setOnClickListener { inputProcessor.onClick(it, layoutPosition) }
        }

        open fun fill(timeLog: TimeLog) {
            val action = timeLog.actionName
            val start = timeLog.timeBegin
            val end = timeLog.timeEnd
            actionNameText.text = action
            actionTimesText.text = formatTimes(start, end)
            val url = view.context.resources.getString(R.string.icons_base) + "/" + ImageMap[action]
            Picasso.with(view.context)
                    .load(url)
                    .into(actionImage)
        }

        open fun formatTimes(start: Date, end: Date?): CharSequence {
            val from = formatDateTime(view.context, start.time)
            val to = formatDateTime(view.context, end?.time ?: System.currentTimeMillis())
            return view.context.getString(R.string.pattern_time_range, from, to)
        }
    }

    inner class UncompletedViewHolder constructor(view: View) : ViewHolder(view) {
        val pauseButton = view.findViewById(R.id.btn_pause)
        val stopButton = view.findViewById(R.id.btn_stop)

        init {
            pauseButton.setOnClickListener { inputProcessor.onClick(it, layoutPosition) }
            stopButton.setOnClickListener { inputProcessor.onClick(it, layoutPosition) }
        }

        override fun formatTimes(start: Date, end: Date?): CharSequence {
            val from = formatDateTime(view.context, start.time)
            return from
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        when (viewType) {
            UNCOMPLETED -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.time_log_row_uncompleted, parent, false)
                return UncompletedViewHolder(view)

            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                        .inflate(R.layout.time_log_row, parent, false)
                return ViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fill(dataset[position])
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun getItemViewType(position: Int): Int {
        if (dataset[position].timeEnd == null) {
            return UNCOMPLETED
        } else {
            return NORMAL
        }
    }

    private inner class InputProcessor {
        fun onClick(v: View, index: Int) {
            when (v.id) {
                R.id.btn_pause -> Toast.makeText(v.context, "Pause", Toast.LENGTH_SHORT).show()
                R.id.btn_stop -> Toast.makeText(v.context, "Stop", Toast.LENGTH_SHORT).show()
                R.id.layout_time_log_row, R.id.layout_time_log_row_uncompleted -> {
                    val transaction = fragmentManager.beginTransaction()
                    val timeLogDetailFragment = TimeLogDetailFragment(dataset[index])
                    transaction.replace(R.id.activity_main, timeLogDetailFragment)
                    transaction.addToBackStack(null)
                    transaction.commit()
                }
            }
        }

    }
}