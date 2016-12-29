package ru.ktoddler.view.activity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.support.v7.widget.Toolbar
import com.annimon.stream.Optional
import com.annimon.stream.function.Consumer
import ru.ktoddler.KToddlerApp
import ru.ktoddler.di.AppComponent
import ru.ktoddler.util.NpeUtils
import ru.ktoddler.view.behaviour.ToolbarProvider
import ru.ktoddler.view.fragment.BaseFragment
import ru.ktoddler.view.notification.KToddlerNotification
import ru.ktoddler.view.notification.Message
import rx.Subscription
import rx.subscriptions.CompositeSubscription
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), BaseFragment.TitleUpdateListener, ToolbarProvider {

    private var subscription = CompositeSubscription()

    @Inject
    internal lateinit var notification: KToddlerNotification

    fun provideAppComponent() : AppComponent {
        return (application as KToddlerApp).appComponent
    }

    protected fun <T : ViewDataBinding> bindView(@LayoutRes layoutId: Int): T {
        return DataBindingUtil.setContentView<T>(this, layoutId)
    }

    protected fun addSubscription(s: Subscription) {
        subscription.add(s)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideAppComponent().inject(this)
        subscription = CompositeSubscription()
    }

    override fun onDestroy() {
        subscription.unsubscribe()
        if (isFinishing) {
            onFinish()
        }
        super.onDestroy()
    }

    protected open fun onFinish() {}

    protected fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(layoutId, fragment)
                .commit()
    }

    protected fun replaceFragment(@IdRes layoutId: Int, fragment: Fragment, backStackName: String) {
        supportFragmentManager.beginTransaction()
                .replace(layoutId, fragment)
                .addToBackStack(backStackName)
                .commit()
    }

    override fun updateTitleRequest(fragment: BaseFragment): Boolean {
        fragment.getTitle()?.let {
            supportActionBar?.let {
                it.title = fragment.getTitle()
                return true
            }
        }
        return false
    }

    protected fun showToast(message: Message) {
        notification.showToast(message)
    }

    protected fun showSnackbar(message: Message) {
        notification.showSnackbar(this, null, message, null)
    }

    protected fun hasBackStack(): Boolean {
        return supportFragmentManager.backStackEntryCount > 0
    }

    protected val extras: Bundle
        get() = Optional.ofNullable(intent.extras).orElse(Bundle())

    protected fun <T : DialogFragment> showDialog(dialog: T) {
        if (!isFragmentActive(dialog.javaClass.simpleName)) {
            dialog.show(supportFragmentManager, dialog.javaClass.simpleName)
        }
    }

    private fun isFragmentActive(tag: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(tag)
        return fragment != null && !fragment.isDetached && !fragment.isRemoving
    }

    protected fun hideDialog(dialog: Class<*>) {
        NpeUtils.call(supportFragmentManager.findFragmentByTag(dialog.simpleName),
                DialogFragment::class.java,
                Consumer<DialogFragment>(DialogFragment::dismissAllowingStateLoss))
    }

    protected fun findFragment(@IdRes id: Int): Optional<BaseFragment> {
        return Optional.ofNullable(supportFragmentManager.findFragmentById(id) as BaseFragment)
    }

    override fun getToolbar(): Toolbar? {
        return null
    }

    companion object {
        init {
            AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
        }
    }
}
