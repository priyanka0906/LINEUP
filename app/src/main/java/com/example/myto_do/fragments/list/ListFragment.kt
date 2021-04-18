package com.example.myto_do.fragments.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.GridLayout
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.example.myto_do.R
import com.example.myto_do.data.MyToDoViewModel
import com.example.myto_do.databinding.FragmentListFragmentBinding
import com.example.myto_do.fragments.SharedViewModel
import com.example.myto_do.fragments.list.adapter.ListAdapter
import com.example.myto_do.models.ToDoData
import com.google.android.material.snackbar.Snackbar

class ListFragment : Fragment(),SearchView.OnQueryTextListener {
    private val mToDoViewModel:MyToDoViewModel by viewModels()
   private val adapter: ListAdapter by lazy{ ListAdapter() }
    private val mSharedViewModel:SharedViewModel by viewModels()
    private var  _binding: FragmentListFragmentBinding? =null
    private val binding get()=_binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Data Binding

        _binding=FragmentListFragmentBinding.inflate(inflater,container,false)

        binding.lifecycleOwner = this
        binding.mSharedViewModel=mSharedViewModel

        // setup recyclerView
        setUpRecyclerView()

        setHasOptionsMenu(true)
        return binding.root
    }

    private fun setUpRecyclerView() {
        val recyclerView= _binding?.recyclerView
        recyclerView?.adapter =adapter
        recyclerView?.layoutManager =StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        mToDoViewModel.getAllData.observe(viewLifecycleOwner, { data->
            mSharedViewModel.checkIfEmpty(data)
            adapter.setData(data)
        })

        if (recyclerView != null) {
            swipeToDelete(recyclerView)
        }
    }
  private fun swipeToDelete(recyclerView: RecyclerView)
  { val swipeToDeleteCallBack=object:SwipeToDelete()
  {
      override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
          val deletedItem=adapter.dataList[viewHolder.adapterPosition]
         mToDoViewModel.deleteItem(deletedItem)
          adapter.notifyItemChanged(viewHolder.adapterPosition)

          //Restore Data
          restoreData(viewHolder.itemView,deletedItem,viewHolder.adapterPosition)
      }

  }
      val itemTouchHelper=ItemTouchHelper(swipeToDeleteCallBack)
      itemTouchHelper.attachToRecyclerView(recyclerView)

  }
    private fun restoreData(view: View,deletedItem:ToDoData,position:Int)
    {
        val snackbar=Snackbar.make(view,"Deleted ${deletedItem.title}",Snackbar.LENGTH_LONG)
        snackbar.setAction("Undo"){
            mToDoViewModel.insertData(deletedItem)
            adapter.notifyItemChanged(position)

        }
        snackbar.show()
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
        val search = menu.findItem(R.id.search)
        val searchView = search.actionView as SearchView
        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.delete_all->confirmRemoval()
            R.id.high_priority ->mToDoViewModel.sortByHighPriority.observe(this, Observer{ adapter.setData(it)

            })
            R.id.low_priority->mToDoViewModel.sortByLowPriority.observe(this, Observer { adapter.setData(it) })
        }

        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder= AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(),"Successfully removed everything!!", Toast.LENGTH_SHORT).show()

        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete everything?")
        builder.setMessage("Are you sure you want to delete everything?")
        builder.create().show()
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query!=null)
        {
            searchThroughdatabase(query)
        }
return true
    }


    override fun onQueryTextChange(query: String?): Boolean {
        if(query!=null)
        {
            searchThroughdatabase(query)
        }
        return true
    }

    private fun searchThroughdatabase(query: String) {
    var searchQuery=query
        searchQuery="%$searchQuery%"
        mToDoViewModel.searchDataBase(searchQuery).observe(this, Observer {list->
            list?.let{
                adapter.setData(it)
            }
        })

    }
}