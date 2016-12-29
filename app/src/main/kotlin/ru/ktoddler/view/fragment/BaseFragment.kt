package ru.ktoddler.view.fragment

import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import com.annimon.stream.function.Consumer
import ru.ktoddler.di.AppComponent
import ru.ktoddler.util.NpeUtils
import ru.ktoddler.view.activity.BaseActivity
import ru.ktoddler.view.behaviour.CallbackProvider
import ru.ktoddler.view.notification.Action
import ru.ktoddler.view.notification.KToddlerNotification
import ru.ktoddler.view.notification.Message
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

abstract class BaseFragment : Fragment() {

    interface TitleUpdateListener {
        fun updateTitleRequest(fragment: BaseFragment): Boolean
    }

    private val subscription = CompositeSubscription()

    @Inject
    internal lateinit var notification: KToddlerNotification

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

    protected fun <T> getCallback(callbackClass: Class<T>): T? {
        val hostParent = hostParent
        if (hostParent is CallbackProvider) {
            return hostParent.getCallback(callbackClass)
        } else if (callbackClass.isInstance(hostParent)) {
            return callbackClass.cast(hostParent)
        }
        return null
    }

    protected fun updateTitleRequest(): Boolean {
        var titleUpdated = false
        val listener = getCallback(TitleUpdateListener::class.java)
        if (listener != null) {
            titleUpdated = listener.updateTitleRequest(this)
        }

        return titleUpdated
    }

    fun getTitle() : String? {
        return getInstanceTitle()
    }

    protected fun getInstanceTitle() : String? {
        return null
    }

    protected fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideAppComponent().inject(this)
    }

    override fun onResume() {
        super.onResume()
        updateTitleRequest()
    }

    override fun onStop() {
        super.onStop()
        // TODO repair this
        // ViewUtils.hideKeyboard(getActivity());
    }

    override fun onDestroy() {
        if (isRemoving || activity.isFinishing) {
            onFinish()
        }
        super.onDestroy()
    }

    override fun onDestroyView() {
        subscription.unsubscribe()
        super.onDestroyView()
    }

    protected fun onFinish() {}

    fun onBackPressed(): Boolean {
        return false
    }

    fun hasBackStack(): Boolean {
        return false
    }

    protected fun verifyGrantedPermission(grantResults: IntArray): Boolean {
        for (result in grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    protected fun showToast(message: Message) {
        notification.showToast(message)
    }

    protected fun showSnackbar(message: Message, action: Action) {
        notification.showSnackbar(activity, null, message, action)
    }

    protected fun provideAppComponent() : AppComponent {
        return (activity as BaseActivity).provideAppComponent()
    }

    protected fun <T : DialogFragment> showDialog(dialog: T) {
        if (!isFragmentActive(dialog.javaClass.simpleName)) {
            dialog.show(childFragmentManager, dialog.javaClass.simpleName)
        }
    }

    private fun isFragmentActive(tag: String): Boolean {
        val fragment = childFragmentManager.findFragmentByTag(tag)
        return fragment != null && !fragment.isDetached && !fragment.isRemoving
    }

    protected fun hideDialog(dialog: Class<*>) {
        NpeUtils.call(childFragmentManager.findFragmentByTag(dialog.simpleName),
                DialogFragment::class.java,
                Consumer<DialogFragment>(DialogFragment::dismissAllowingStateLoss))
    }
}
