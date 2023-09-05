package com.example.news.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.news.models.Item
import com.example.news.utils.Constants.NEWS_TABLE

//id = 0 chu de giai tri
//id = 1 chu de the gioi
@Entity(tableName = NEWS_TABLE)
class NewsEntity(var item : Item) {

    @PrimaryKey(autoGenerate = false)
    var id : Int = 0
}