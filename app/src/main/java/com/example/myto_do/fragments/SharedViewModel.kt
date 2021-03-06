package com.example.myto_do.fragments

import android.app.Application
import android.text.TextUtils
import android.view.View
import android.widget.AdapterView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myto_do.R
import com.example.myto_do.models.Priority
import com.example.myto_do.models.ToDoData

class SharedViewModel(application: Application):AndroidViewModel(application) {
    var emptyDataBase:MutableLiveData<Boolean> = MutableLiveData(false)
    fun checkIfEmpty(tododata:List<ToDoData>)
    {
        emptyDataBase.value= tododata.isEmpty()
    }
    val listener:AdapterView.OnItemSelectedListener=object:
        AdapterView.OnItemSelectedListener{
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long) {
            when(position){
                0-> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.red)) }
                1-> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.yellow)) }
                2-> {(parent?.getChildAt(0) as TextView).setTextColor(ContextCompat.getColor(application,R.color.green))}
            }
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

    fun verifyData( title:String, description:String):Boolean
    {
        return if(TextUtils.isEmpty(title)|| TextUtils.isEmpty(description))
            false
        else
            !(title.isEmpty()||description.isEmpty())


    }
     fun parsePriority(priority:String): Priority {
        return when(priority) {
            "High Priority"-> {
                Priority.HIGH}
            "Medium Priority"-> {
                Priority.MEDIUM}
            "Low Priority"-> {
                Priority.LOW}
            else-> { Priority.LOW}
        }
    }

}
