package ru.michaelilyin.mobileapplication.service.command

import android.util.Log

/**
 * Created by micha on 12.03.2017.
 */
class CountDownCmd : AbstractCmd<Unit>() {

    private val tag = CountDownCmd::class.java.name

    override fun call() {
        for (i in 0 .. 10) {
            Log.i(tag, "Count down command $i")
            Thread.sleep(1000)
        }
    }
}