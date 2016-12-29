package ru.ktoddler.view.notification

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
import ru.ireca.guest.view.notification.Duration
import ru.ireca.guest.view.notification.Type
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
            Math.abs(message1.createonDateMillis - message2.createonDateMillis) < EQUAL_MESSAGE_INTERVAL_MILLIS
                    && message1 == message2
        }
                .subscribe({ this.showToastInternal(it) }, { Timber.e(it) })
    }

    fun showToast(message: Message) {
        messagesBus.onNext(message)
    }

    private fun showToastInternal(message: Message) {
        val duration: Int
        when (message.getDuration()) {
            Duration.INDEFINITE -> duration = Toast.LENGTH_LONG
            Duration.LONG -> duration = Toast.LENGTH_LONG
            Duration.SHORT -> duration = Toast.LENGTH_SHORT
            else -> duration = Toast.LENGTH_SHORT
        }

        val toast = Toast.makeText(context, message.getText(), duration)
        val toastView = LayoutInflater.from(context)
                .inflate(R.layout.view_toast, null, false) as TextView
        toastView.background = ContextCompat.getDrawable(context, getMessageDrawable(message.getType()))
        toastView.text = message.getText()
        toast.view = toastView
        toast.show()
    }

    fun showSnackbar(activity: Activity, view: View?, message: Message, action: Action? = null) {
        val sb = Snackbar.make(
                view ?: activity.findViewById(android.R.id.content),
                message.getText(),
                message.getDuration().type)

        if (action != null) {
            val text : String
            if (action.actionResId > 0) {
                text = context.getString(action.actionResId)
            } else{
                text = action.actionText
            }
            sb.setAction(text, action.getListener())
            val actionColor = ContextCompat.getColor(activity, getActionColor(message.getType()))
            sb.setActionTextColor(actionColor)
        }

        val snackBarView = sb.view
        val mesColor = ContextCompat.getColor(activity, getMessageColor(message.getType()))
        snackBarView.setBackgroundColor(mesColor)
        sb.show()
    }

    private fun getMessageDrawable(type: Type): Int {
        when (type) {
            Type.ALERT -> return R.drawable.rounded_button_alert
            Type.CONFIRM -> return R.drawable.rounded_button_confirm
            else -> return R.drawable.rounded_button_info
        }
    }

    @ColorRes
    private fun getMessageColor(type: Type): Int {
        when (type) {
            Type.ALERT -> return R.color.message_alert
            Type.CONFIRM -> return R.color.message_confirm
            else -> return R.color.message_info
        }
    }

    @ColorRes
    private fun getActionColor(type: Type): Int {
        when (type) {
            Type.ALERT -> return R.color.message_action_alert
            Type.CONFIRM -> return R.color.message_action_confirm
            else -> return R.color.message_action_info
        }
    }
}
