package ru.ktoddler.view.adapter.binding

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import java.util.*

abstract class CollectionAdapter<ITEM, VH : RecyclerView.ViewHolder> : RecyclerView.Adapter<VH>() {

    open var items = emptyList<ITEM>()

    abstract fun updateItems(newItems: List<ITEM>?)

    protected fun updateList(newItems: List<ITEM>?, diffCallback: DiffUtil.Callback) {
        this.items = newItems?: Collections.emptyList()

        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    open fun getItem(position: Int): ITEM {
        return items[position]
    }

    override fun getItemCount(): Int {
        return items.size
    }
}
