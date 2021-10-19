package com.example.myto_do.fragments.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.myto_do.R
import com.example.myto_do.data.MyToDoViewModel
import com.example.myto_do.databinding.FragmentUpdatedBinding
import com.example.myto_do.fragments.SharedViewModel
import com.example.myto_do.models.ToDoData
import kotlinx.android.synthetic.main.fragment_updated.*


class UpdatedFragment : Fragment() {
    private val args by navArgs<UpdatedFragmentArgs>()
     private val mSharedViewModel:SharedViewModel by viewModels()
    private val mTodoViewModel:MyToDoViewModel by viewModels()
    private var _binding: FragmentUpdatedBinding?=null
    private val binding get()=_binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragments
        _binding=FragmentUpdatedBinding.inflate(inflater,container,false)
        binding.args=args

        setHasOptionsMenu(true)


        binding.spinner.onItemSelectedListener=mSharedViewModel.listener
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        { R.id.update_menu->updateItem()
          R.id.delete->{confirmItemRemoval()}
        }
        return super.onOptionsItemSelected(item)
    }



    private fun updateItem() {
        val title=title.text.toString()
        val description=description.text.toString()
        val priority=spinner.selectedItem.toString()

        val validation=mSharedViewModel.verifyData(title,description);
        if(validation)
         { //updated item
             val updatedItem=ToDoData(
                 args.currentItem.id,
                 title,
                 mSharedViewModel.parsePriority(priority),
                 description

             )
             mTodoViewModel.updateData(updatedItem)
             Toast.makeText(requireContext(),"Successfully Updated!!",Toast.LENGTH_SHORT).show()
             // navigate back
             findNavController().navigate(R.id.action_updatedFragment_to_list_fragment)
        }
        else
        {
            Toast.makeText(requireContext(),"Please fill out all fields!!",Toast.LENGTH_SHORT).show()
        }
    }
    private fun confirmItemRemoval() {
        val builder=AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") {_,_ ->
            mTodoViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(),"Successfully removed!!",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updatedFragment_to_list_fragment)
        }
        builder.setNegativeButton("No"){_,_ ->}
        builder.setTitle("Delete '${args.currentItem.title}'?")
        builder.setMessage("Are you sure you want to delete '${args.currentItem.title}'?")
        builder.create().show()
    }

    override fun onDestroyView() {

        super.onDestroyView()
        _binding=null
    }
}