package com.example.myto_do.fragments

import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.example.myto_do.R
import com.example.myto_do.fragments.list.ListFragmentDirections
import com.example.myto_do.models.Priority
import com.example.myto_do.models.ToDoData
import com.google.android.material.floatingactionbutton.FloatingActionButton

class BindingAdapter {

    companion object {

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view:FloatingActionButton,navigate:Boolean)
        {
            view.setOnClickListener{
                if(navigate)
                    view.findNavController().navigate(R.id.action_list_fragment_to_adderFragment)
            }
        }
        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View,emptyDatabase:MutableLiveData<Boolean>)
        {
            when(emptyDatabase.value )
            {
                true->{view.visibility=View.VISIBLE}
                false->{view.visibility=View.INVISIBLE}

            }

        }
        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view:Spinner,priority: Priority)
        {
            when(priority)
            {
                Priority.HIGH->{view.setSelection(0)}
                Priority.MEDIUM->{view.setSelection(1)}
                Priority.LOW->{view.setSelection(2)}


            }
        }

        @BindingAdapter("android:parsePriorityColor")
        @JvmStatic
        fun parsePriorityColor(cardView: CardView,priority: Priority)
        {
            when(priority)
            {
                Priority.HIGH-> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.red)) }
                Priority.MEDIUM-> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.yellow)) }
                Priority.LOW-> {cardView.setCardBackgroundColor(ContextCompat.getColor(cardView.context, R.color.green)) }
            }
        }
        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view:ConstraintLayout,currentItem: ToDoData)
        {
            view.setOnClickListener{
                val action=ListFragmentDirections.actionListFragmentToUpdatedFragment(currentItem)
                view.findNavController().navigate(action)

            }
        }
    }
}