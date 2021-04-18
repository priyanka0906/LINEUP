package com.example.myto_do.data

import androidx.room.TypeConverter
import com.example.myto_do.models.Priority

class Converter {
    @TypeConverter
    fun fromPriority(priority: Priority):String{
        return priority.name
    }
    @TypeConverter
    fun toPriority(priority:String): Priority {
        return Priority.valueOf(priority)
    }
}