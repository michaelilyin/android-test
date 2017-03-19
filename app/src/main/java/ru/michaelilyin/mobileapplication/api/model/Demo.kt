package ru.michaelilyin.mobileapplication.api.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by micha on 12.03.2017.
 */
data class Demo(
        @SerializedName("id")
        @Expose
        var id: Long,

        @SerializedName("name")
        @Expose
        var name: String
) : Serializable {

    @Suppress("UNUSED")
    constructor() : this(0, "")

}