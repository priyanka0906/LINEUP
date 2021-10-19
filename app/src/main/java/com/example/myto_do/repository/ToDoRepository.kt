package com.example.myto_do.repository

import androidx.lifecycle.LiveData
import com.example.myto_do.data.TODODao
import com.example.myto_do.models.ToDoData

class ToDoRepository( private val todoDao: TODODao
   ) {

    val getAllData: LiveData<List<ToDoData>> = todoDao.getAllData()
    val sortByHighPriority:LiveData<List<ToDoData>> =todoDao.sortByHighPriority()
    val sortByLowPriority:LiveData<List<ToDoData>> =todoDao.sortByLowPriority()
    suspend fun insertData(toDoData: ToDoData)
    {
        todoDao.insertData(toDoData )
    }
    suspend fun updateData(toDoData:ToDoData)
    {
        todoDao.updateItem(toDoData)
    }
   suspend fun deleteItem(toDoData: ToDoData)
    {
        todoDao.deleteItem(toDoData)
    }
    suspend fun deleteAll()
    {
        todoDao.deleteAll()
    }
   fun searchDataBase(searchQuery:String):LiveData<List<ToDoData>>
    {
        return todoDao.searchDataBase(searchQuery)
    }
}
