package ru.ktoddler.presenter

import android.content.Context
import android.support.annotation.StringRes
import com.annimon.stream.Optional
import ru.ktoddler.R
import ru.ktoddler.model.exception.KToddlerException
import ru.ktoddler.util.NpeUtils
import ru.ktoddler.view.View
import timber.log.Timber
import java.lang.ref.WeakReference

abstract class BasePresenter<V : View>(private val context: Context) : Presenter<V> {

    private var view = WeakReference<V>(null)

    override fun bindView(view: V) {
        this.view.clear()
        this.view = WeakReference(view)
    }

    override fun unbindView(view: V) {
        if (NpeUtils.equals(this.view.get(), view)) {
            this.view.clear()
        }
    }

    protected fun showLoadingState(info: String) {
        getView().ifPresent { v -> v.showLoading(info) }
    }

    protected fun hideLoadingState() {
        getView().ifPresent { v -> v.hideLoading() }
    }

    protected fun getView(): Optional<V> {
        return Optional.ofNullable(view.get())
    }

    protected fun handleError(throwable: Throwable?) {
        if (throwable != null) {
            val mes: String
            if (throwable is KToddlerException) {
                mes = getString(R.string.pattern_definition, throwable.title, throwable.details)
            } else {
                val details = if (NpeUtils.isEmpty(throwable.message)) throwable.toString() else throwable.message
                mes = getString(R.string.error_unknown_with_description, details as Any)
            }
            showAlert(mes)

            Timber.e(throwable, mes)
        }
    }

    protected fun showAlert(error: String) {
        getView().ifPresent { v -> v.showError(error) }
    }

    protected fun showInfo(info: String) {
        getView().ifPresent { v -> v.showInfo(info) }
    }

    protected fun showConfirm(confirm: String) {
        getView().ifPresent { v -> v.showConfirm(confirm) }
    }

    fun getString(@StringRes resId: Int): String {
        return context.getString(resId)
    }

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String {
        return context.getString(resId, *formatArgs)
    }

    override fun onFinish() {}
}
