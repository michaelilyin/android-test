package ru.michaelilyin.mobileapplication.api

import com.google.gson.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.michaelilyin.mobileapplication.R
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by micha on 19.03.2017.
 */
class ApiModule(host: String,
                port: String,
                root: String) {

    private val retrofit: Retrofit

    val demoApi: DemoApi
    val timeLogApi: TimeLogApi

    init {
        val gson = GsonBuilder()
                .registerTypeAdapter(Date::class.java, DateTimeDeserializer())
                .registerTypeAdapter(Date::class.java, DateTimeSerializer())
                .create()
        val retrofitGson = GsonConverterFactory.create(gson)
        retrofit = Retrofit.Builder()
                .baseUrl("$host:$port$root")
                .addConverterFactory(retrofitGson)
                .build()
        demoApi = retrofit.create(DemoApi::class.java)
        timeLogApi = retrofit.create(TimeLogApi::class.java)
    }

    private inner class DateTimeDeserializer : JsonDeserializer<Date> {
        private val offset = TimeZone.getDefault().rawOffset

        override fun deserialize(json: JsonElement, typeOfT: Type,
                                 context: JsonDeserializationContext): Date {
            return Date(json.asString.toLong() - offset)
        }
    }

    private inner class DateTimeSerializer : JsonSerializer<Date> {
        private val offset = TimeZone.getDefault().rawOffset

        override fun serialize(src: Date?, typeOfSrc: Type,
                               context: JsonSerializationContext): JsonElement {
            if (src == null) {
                return JsonNull.INSTANCE
            }
            return JsonPrimitive(src.time + offset)
        }
    }

}