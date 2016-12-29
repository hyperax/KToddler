package ru.ktoddler.view.adapter.binding

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BindingAdapter<BINDING : ViewDataBinding>
(@LayoutRes private val layoutResourceId: Int) : RecyclerView.Adapter<BindingViewHolder<BINDING>>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindingViewHolder<BINDING> {
        val binding = DataBindingUtil.inflate<BINDING>(LayoutInflater.from(parent.context), layoutResourceId, parent, false)
        return BindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<BINDING>, position: Int) {
        val binding = holder.binding
        updateBinding(binding, position)
    }

    protected abstract fun updateBinding(binding: BINDING, position: Int)

}
