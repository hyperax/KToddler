package ru.ktoddler.view.adapter

import android.support.v7.util.DiffUtil

abstract class DiffCallback<ITEM>(protected var newItems: List<ITEM>,
                                  protected var oldItems: List<ITEM>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    abstract override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean

    abstract override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean
}
