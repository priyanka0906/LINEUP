package com.example.myto_do.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myto_do.models.ToDoData
import com.example.myto_do.repository.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MyToDoViewModel(application: Application):AndroidViewModel(application) {
  private val toDoDao= ToDoDataBase.getDataBase(application)?.todoDao()

    private val repository:ToDoRepository
     val getAllData:LiveData<List<ToDoData >>
    val sortByHighPriority:LiveData<List<ToDoData>>
     val sortByLowPriority:LiveData<List<ToDoData>>


    init{
        repository= ToDoRepository(toDoDao!!)
        getAllData=repository.getAllData
        sortByHighPriority=repository.sortByHighPriority
        sortByLowPriority=repository.sortByLowPriority
    }

    fun insertData(toDoData: ToDoData)
    {
        viewModelScope.launch(Dispatchers.IO ){
            repository.insertData(toDoData)
        }
    }
    fun updateData(toDoData: ToDoData)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.updateData(toDoData)
        }
    }
    fun deleteItem(toDoData: ToDoData)
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteItem(toDoData)
        }
    }
    fun deleteAll()
    {
        viewModelScope.launch(Dispatchers.IO)
        {
            repository.deleteAll()
        }
    }
    fun searchDataBase(searchQuery:String):LiveData<List<ToDoData>>
    { return repository.searchDataBase(searchQuery)

    }
}
