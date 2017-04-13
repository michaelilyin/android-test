package ru.michaelilyin.mobileapplication.service

import android.app.Activity
import android.os.Message
import java.lang.ref.WeakReference

/**
 * Created by micha on 23.03.2017.
 */
class ContextHandler<T> constructor(
        activity: T,
        private val callback: ((T, Message) -> Unit)
) : android.os.Handler() {

    private val activity = WeakReference(activity)

    override fun handleMessage(msg: Message?) {
        val activity = activity.get()
        if (activity != null && msg != null) {
            callback(activity, msg)
        }
    }
}
