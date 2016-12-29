package ru.ktoddler.view.notification

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.support.annotation.ColorRes
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import android.widget.Toast
import io.reactivex.subjects.PublishSubject
import ru.ktoddler.R
import ru.ktoddler.di.scope.PerApplication
import timber.log.Timber
import javax.inject.Inject

@PerApplication
class KToddlerNotification
@Inject
constructor(private val context: Context) {

    private val EQUAL_MESSAGE_INTERVAL_MILLIS: Long = 2000

    private val messagesBus = PublishSubject.create<Message>()

    init {
        messagesBus.distinctUntilChanged {
            message1, message2 ->
            Math.abs(message1.creationDateMillis() - message2.creationDateMillis()) < EQUAL_MESSAGE_INTERVAL_MILLIS
                    && message1 == message2
        }
                .subscribe({ this.showToastInternal(it) }, { Timber.e(it) })
    }

    fun showToast(message: Message) {
        messagesBus.onNext(message)
    }

    private fun showToastInternal(message: Message) {
        val duration: Int
        when (message.duration) {
            Message.INDEFINITE -> duration = Toast.LENGTH_LONG
            Message.LONG -> duration = Toast.LENGTH_LONG
            Message.SHORT -> duration = Toast.LENGTH_SHORT
            else -> duration = Toast.LENGTH_SHORT
        }

        val toast = Toast.makeText(context, message.text, duration)
        val toastView = LayoutInflater.from(context)
                .inflate(R.layout.view_toast, null, false) as TextView
        toastView.background = ContextCompat.getDrawable(context, getMessageDrawable(message.type))
        toastView.text = message.text
        toast.view = toastView
        toast.show()
    }

    fun showSnackbar(activity: Activity, view: View?, message: Message, action: Action? = null) {
        val sb = Snackbar.make(
                view ?: activity.findViewById(android.R.id.content),
                message.text,
                message.duration)

        if (action != null) {
            if (action.actionResId != 0) {
                sb.setAction(action.actionResId, action.listener)
            } else {
                sb.setAction(action.actionText, action.listener)
            }
            val actionColor = ContextCompat.getColor(activity, getActionColor(message.type))
            sb.setActionTextColor(actionColor)
        }

        val snackBarView = sb.view
        val mesColor = ContextCompat.getColor(activity, getMessageColor(message.type))
        snackBarView.setBackgroundColor(mesColor)
        sb.show()
    }

    @SuppressLint("SwitchIntDef")
    @ColorRes
    private fun getMessageColor(@Message.Type type: Int): Int {
        when (type) {
            Message.ALERT -> return R.color.message_alert
            Message.CONFIRM -> return R.color.message_confirm
            else -> return R.color.message_info
        }
    }

    @SuppressLint("SwitchIntDef")
    private fun getMessageDrawable(@Message.Type type: Int): Int {
        when (type) {
            Message.ALERT -> return R.drawable.rounded_button_alert
            Message.CONFIRM -> return R.drawable.rounded_button_confirm
            else -> return R.drawable.rounded_button_info
        }
    }

    @SuppressLint("SwitchIntDef")
    @ColorRes
    private fun getActionColor(@Message.Type type: Int): Int {
        when (type) {
            Message.ALERT -> return R.color.message_action_alert
            Message.CONFIRM -> return R.color.message_action_confirm
            else -> return R.color.message_action_info
        }
    }
}
