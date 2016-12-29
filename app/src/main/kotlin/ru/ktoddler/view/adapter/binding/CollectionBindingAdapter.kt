package ru.ktoddler.view.adapter.binding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class CollectionBindingAdapter<ITEM, BINDING : ViewDataBinding>
(@LayoutRes private val layoutResourceId: Int) : CollectionAdapter<ITEM, BindingViewHolder<BINDING>>() {

    override var items: List<ITEM>
        get() = items
        set(value) {
            super.items = value
        }

    override fun getItem(position: Int): ITEM {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<BINDING> {
        val binding = DataBindingUtil.inflate<BINDING>(LayoutInflater.from(parent.context), layoutResourceId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<BINDING>, position: Int) {
        val binding = holder.binding
        updateBinding(binding, getItem(position), position)
    }

    protected abstract fun updateBinding(binding: BINDING, item: ITEM, position: Int)
}
