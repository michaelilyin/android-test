package ru.michaelilyin.mobileapplication.api.command

/**
 * Created by micha on 22.03.2017.
 */
class ApiException(val code: Int, override val message: String = "") : RuntimeException()