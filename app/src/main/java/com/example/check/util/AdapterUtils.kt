package com.example.check.util

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView


class RecyclerViewAdapter : ListAdapter<RecyclerItem, BindingViewHolder>(DiffCallback()) {


    override fun getItemViewType(position: Int): Int {
        return getItem(position).layoutId
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ViewDataBinding = DataBindingUtil.inflate(inflater, viewType, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int) {
        getItem(position).bind(holder.binding)
        holder.binding.executePendingBindings()
    }


}


private class DiffCallback : DiffUtil.ItemCallback<RecyclerItem>() {
    override fun areItemsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.modelData
        val newData = newItem.modelData
        // Use appropriate comparator's method if both items implement the interface
        // and rely on plain 'equals' otherwise
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameItem(newData)
        } else oldData == newData
    }

    override fun areContentsTheSame(
        oldItem: RecyclerItem,
        newItem: RecyclerItem
    ): Boolean {
        val oldData = oldItem.modelData
        val newData = newItem.modelData
        return if (oldData is RecyclerItemComparator
            && newData is RecyclerItemComparator
        ) {
            oldData.isSameContent(newData)
        } else oldData == newData
    }
}

class BindingViewHolder(
    val binding: ViewDataBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun updateBinding(variableId: Int, value: Any) {
        binding.setVariable(variableId, value)
    }
}

data class RecyclerItem(
    val modelData: Pair<Int, Any>,
    val data: List<Pair<Int, Any>>,
    @LayoutRes val layoutId: Int
) {
    fun bind(binding: ViewDataBinding) {
        binding.setVariable(modelData.first, modelData.second)
        data.forEach {
            binding.setVariable(it.first, it.second)
        }
    }
}

inline fun <reified T : Any> RecyclerItem.model(): T {
    return this.modelData.second as T
}


@BindingAdapter("android:recycler_view_items")
fun setRecyclerViewAdapterItems(
    recyclerView: RecyclerView,
    items: List<RecyclerItem>?
) {
    var adapter = (recyclerView.adapter as? RecyclerViewAdapter)
    recyclerView.itemAnimator = null
    if (adapter == null) {
        adapter = RecyclerViewAdapter()
        recyclerView.adapter = adapter
    }

    adapter.submitList(items.orEmpty())
}


interface RecyclerItemComparator {
    fun isSameItem(other: Any): Boolean
    fun isSameContent(other: Any): Boolean
}