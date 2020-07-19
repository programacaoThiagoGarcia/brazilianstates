package com.programacaothiagogarcia.brazilstate.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.programacaothiagogarcia.brazilstate.R
import com.programacaothiagogarcia.brazilstate.databinding.StateItemBinding
import com.programacaothiagogarcia.brazilstate.model.State

class StatesAdapter(private val context: Context) :
    RecyclerView.Adapter<StatesAdapter.StatesAdapterViewHolder>() {
    var stateList: List<State>? = null
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    class StatesAdapterViewHolder( private val binding: StateItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun setItem(state: State) {
            binding.item = state
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatesAdapterViewHolder {
        val binding : StateItemBinding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.state_item,parent,false)
        return StatesAdapterViewHolder(binding)
    }

    override fun getItemCount() = stateList?.size ?: 0

    override fun onBindViewHolder(holder: StatesAdapterViewHolder, position: Int) {
         stateList?.let {
             val state : State = it[position]
             holder.setItem(state)
         }
    }

}
