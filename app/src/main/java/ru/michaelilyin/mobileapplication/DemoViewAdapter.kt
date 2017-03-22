package ru.michaelilyin.mobileapplication

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import android.widget.TextView
import ru.michaelilyin.mobileapplication.api.model.Demo

/**
 * Created by micha on 19.03.2017.
 */
class DemoViewAdapter(
        private var dataset: List<Demo> = emptyList()
) : RecyclerView.Adapter<DemoViewAdapter.ViewHolder>() {

    class ViewHolder(val view: TextView) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = TextView(parent.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.text = dataset[position].name
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}