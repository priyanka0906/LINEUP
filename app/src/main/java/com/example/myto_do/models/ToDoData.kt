package com.example.myto_do.models

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myto_do.models.Priority
import kotlinx.android.parcel.Parcelize

@Entity(tableName= "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id:Int,
    var title:String,
    var priority: Priority,
    var description:String
):Parcelable
