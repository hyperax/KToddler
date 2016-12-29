package ru.ktoddler.view.adapter.binding

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

class BindingViewHolder<out BINDING : ViewDataBinding>(val binding: BINDING) :
        RecyclerView.ViewHolder(binding.root)
