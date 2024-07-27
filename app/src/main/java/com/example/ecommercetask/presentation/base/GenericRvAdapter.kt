package com.example.ecommercetask.presentation.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class GenericRvAdapter<RvItem : Any, VB : ViewBinding>(
    private val inflater: (LayoutInflater, ViewGroup, Boolean) -> VB,
    private val bindViewHolder: (VB, RvItem, position:Int,MutableList<RvItem>) -> Unit
) : RecyclerView.Adapter<GenericRvAdapter.GenericViewHolder<VB>>() {

    class GenericViewHolder<VB : ViewBinding>(val binding: VB) :
        RecyclerView.ViewHolder(binding.root)

    var itemList: MutableList<RvItem> = mutableListOf()



    fun sendListToAdapter(list: MutableList<RvItem>) {
        itemList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericViewHolder<VB> {
        val binding = inflater.invoke(LayoutInflater.from(parent.context), parent, false)

        return GenericViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: GenericViewHolder<VB>,
        position: Int
    ) {
        val item = itemList[position]
        bindViewHolder.invoke(holder.binding, item,position,itemList)
    }

    override fun getItemCount(): Int = itemList.size
}