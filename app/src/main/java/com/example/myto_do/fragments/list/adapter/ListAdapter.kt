package com.example.myto_do.fragments.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myto_do.databinding.RowLayoutBinding
import com.example.myto_do.models.ToDoData

class ListAdapter: RecyclerView.Adapter<ListAdapter.MyViewHolder>() {
    var dataList= emptyList<ToDoData>()
    class MyViewHolder(private val binding:RowLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(toDoData: ToDoData){
           binding.todoData=toDoData
           binding.executePendingBindings()
       }
        companion object{
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater=LayoutInflater.from(parent.context)
                val binding=RowLayoutBinding.inflate(layoutInflater,parent,false)
               return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
          }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       val currentItem=dataList[position]
        holder.bind(currentItem)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun setData(todoData:List<ToDoData>)
    {  val toDoDiffUtil=ToDoDiffUtil(dataList,todoData)
        val todoDiffUtilResult=DiffUtil.calculateDiff(toDoDiffUtil)
        this.dataList=todoData
        todoDiffUtilResult.dispatchUpdatesTo(this)

    }
}