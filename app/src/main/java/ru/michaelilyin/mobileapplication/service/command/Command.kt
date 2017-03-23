package ru.michaelilyin.mobileapplication.service.command

import java.io.Serializable
import java.util.concurrent.Callable

/**
 * Created by micha on 12.03.2017.
 */
interface Command<R> : Callable<R>, Serializable