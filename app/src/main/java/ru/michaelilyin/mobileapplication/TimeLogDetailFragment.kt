package ru.michaelilyin.mobileapplication

import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.michaelilyin.mobileapplication.api.model.TimeLog
import ru.michaelilyin.mobileapplication.utils.ImageMap
import ru.michaelilyin.mobileapplication.utils.formatDateTime
import ru.michaelilyin.mobileapplication.utils.resettableLazy
import ru.michaelilyin.mobileapplication.utils.resettableManager

/**
 * Created by micha on 18.04.2017.
 */
class TimeLogDetailFragment(private val data: TimeLog): Fragment() {

    private val lazyMgr = resettableManager()
    private val actionLabel by resettableLazy(lazyMgr) {
        view.findViewById(R.id.time_log_action) as TextView
    }
    private val timeFromLabel by resettableLazy(lazyMgr) {
        view.findViewById(R.id.time_from) as TextView
    }
    private val timeToLabel by resettableLazy(lazyMgr) {
        view.findViewById(R.id.time_to) as TextView
    }
    private val descriptionLabel by resettableLazy(lazyMgr) {
        view.findViewById(R.id.description) as TextView
    }
    private val actionIcon by resettableLazy(lazyMgr) {
        view.findViewById(R.id.time_log_action_icon) as ImageView
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.time_log_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionLabel.text = data.actionName
        timeFromLabel.text = formatDateTime(view.context, data.timeBegin.time)
        if (data.timeEnd != null) {
            timeToLabel.text = formatDateTime(view.context, data.timeEnd!!.time)
        }
        descriptionLabel.text = "Some mock test for test description label"

        val url = view.context.resources.getString(R.string.icons_base) + "/" + ImageMap[data.actionName]
        Picasso.with(view.context)
                .load(url)
                .into(actionIcon)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        lazyMgr.reset()
    }
}