package com.example.myto_do.fragments.add

import android.os.Bundle
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.example.myto_do.R
import com.example.myto_do.data.MyToDoViewModel
import com.example.myto_do.fragments.SharedViewModel
import com.example.myto_do.models.Priority
import com.example.myto_do.models.ToDoData
import kotlinx.android.synthetic.main.fragment_adder.*
import kotlinx.android.synthetic.main.fragment_adder.view.*


class AdderFragment : Fragment() {

 private  val mToDoMyToDoViewModel: MyToDoViewModel by viewModels()
 private val mSharedViewModel:SharedViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view=inflater.inflate(R.layout.fragment_adder, container, false)

        setHasOptionsMenu(true)
      view.priority_spinner.onItemSelectedListener= mSharedViewModel.listener
         return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId==R.id.menu_add)
        {
            insertToDb()

        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertToDb() {
        val mtitle=current_title.text.toString()
        val mpriority=priority_spinner.selectedItem.toString()
        val mdescription=current_description.text.toString()
        val validation=mSharedViewModel.verifyData(mtitle,mdescription)
        if(validation)
        {
            //insert data
           val newData=ToDoData(
               0,
               mtitle,
               mSharedViewModel.parsePriority(mpriority),
               mdescription
           )
            mToDoMyToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(),"Successfully added!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_adderFragment_to_list_fragment)
        }
        else{
            Toast.makeText(requireContext(),"Please fill out all the fields!",Toast.LENGTH_SHORT).show()
        }
    }

}