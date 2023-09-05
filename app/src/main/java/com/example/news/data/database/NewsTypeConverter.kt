package com.example.news.data.database

import androidx.room.TypeConverter
import com.example.news.models.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class NewsTypeConverter {

    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(item : Item): String {
        return gson.toJson(item)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): Item {
        val listType = object : TypeToken<Item>() {}.type
        return gson.fromJson(data, listType)
    }

}