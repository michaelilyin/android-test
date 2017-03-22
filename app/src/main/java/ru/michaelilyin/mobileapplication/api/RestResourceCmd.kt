package ru.michaelilyin.mobileapplication.api

import retrofit2.Response
import ru.michaelilyin.mobileapplication.Application
import ru.michaelilyin.mobileapplication.api.ApiModule
import ru.michaelilyin.mobileapplication.api.command.ApiException
import ru.michaelilyin.mobileapplication.api.model.Demo
import ru.michaelilyin.mobileapplication.service.command.AbstractCmd
import java.util.*

/**
 * Created by micha on 12.03.2017.
 */
abstract class RestResourceCmd<R> : AbstractCmd<R>() {

    private val tag = RestResourceCmd::class.java.name

    protected val apiModule: ApiModule
        get() = Application.apiModule

    protected fun <E> List<E>.toArrayList(): ArrayList<E> {
        return ArrayList(this)
    }
}