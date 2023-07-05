package by.zharikov.cleanarchitecturenoteapp.core.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ColorMapping {

    companion object {
        @JvmStatic
        @TypeConverter
        fun fromString(value: String): Pair<String, Int> {
            val pairType = object : TypeToken<Pair<String, Int>>() {}.type
            return Gson().fromJson(value, pairType)

        }

        @JvmStatic
        @TypeConverter
        fun fromStringMap(pair: Pair<String, Int>): String {
            val gson = Gson()
            return gson.toJson(pair)
        }
    }
}

object ColorTags {
    const val RED_ORANGE = "RedOrange"
    const val LIGHT_GREEN = "LightGreen"
    const val BABY_BLUE = "BabyBlue"
    const val RED_PINK = "RedPink"
    const val VIOLET = "Violet"
}