package ru.ktoddler.view.dialog

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatDialogFragment
import android.widget.Button
import ru.ktoddler.di.AppComponent
import ru.ktoddler.view.activity.BaseActivity
import ru.ktoddler.view.behaviour.CallbackProvider
import ru.ktoddler.view.notification.KToddlerNotification
import ru.ktoddler.view.notification.Message
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

open class BaseDialogFragment : AppCompatDialogFragment() {

    private val subscription = CompositeSubscription()

    @Inject
    internal lateinit var notification: KToddlerNotification

    init {
        arguments = Bundle() // prevents null-pointer exception on getArguments call
    }

    protected fun provideAppComponent(): AppComponent {
        return (activity as BaseActivity).provideAppComponent()
    }

    protected val hostParent: Any?
        get() {
            var hostParent: Any? = null
            if (parentFragment != null) {
                hostParent = parentFragment
            } else if (activity != null) {
                hostParent = activity
            }
            return hostParent
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideAppComponent().inject(this)
    }

    protected fun <T> getCallback(callbackClass: Class<T>): T? {
        val hostParent = hostParent
        if (hostParent is CallbackProvider) {
            return hostParent.getCallback(callbackClass)
        } else if (callbackClass.isInstance(hostParent)) {
            return callbackClass.cast(hostParent)
        }
        return null
    }

    fun onBackPressed(): Boolean {
        return false
    }

    fun hasBackStack(): Boolean {
        return false
    }

    protected fun getButton(which: Int): Button {
        return (dialog as AlertDialog).getButton(which)
    }

    protected fun showToast(message: Message) {
        notification.showToast(message)
    }

    protected fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun onDestroyView() {
        subscription.unsubscribe()
        super.onDestroyView()
    }

    override fun onDestroy() {
        if (isRemoving || activity.isFinishing) {
            onFinish()
        }
        super.onDestroy()
    }

    protected open fun onFinish() {}
}
