package ru.ktoddler.view.notification

import android.support.annotation.StringRes
import android.view.View
import java.lang.ref.WeakReference

class Action private constructor(
        @StringRes
        val actionResId: Int,
        val actionText: String,
        listener: View.OnClickListener?) {

    private val listener = WeakReference<View.OnClickListener>(listener)

    fun getListener(): View.OnClickListener {
        return listener.get()
    }

    companion object {

        operator fun get(@StringRes textId: Int, actionListener: View.OnClickListener?): Action {
            return Action(textId, "", actionListener)
        }

        operator fun get(actionText: String, actionListener: View.OnClickListener?): Action {
            return Action(0, actionText, actionListener)
        }
    }
}