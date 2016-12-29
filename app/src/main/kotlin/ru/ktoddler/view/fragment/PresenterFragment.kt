package ru.ktoddler.view.fragment

import android.os.Bundle

import com.annimon.stream.function.Consumer

import ru.ktoddler.presenter.Presenter
import ru.ktoddler.util.NpeUtils
import ru.ktoddler.view.View
import ru.ktoddler.view.dialog.SpinnerProgressDialog
import ru.ktoddler.view.notification.Message

abstract class PresenterFragment<P : Presenter<V>, V : View> : BaseFragment(), View {

    private var dialog: SpinnerProgressDialog? = null

    init {
        arguments = Bundle() // prevent NPE when getting args inside fragment's lifecycle
    }

    protected abstract val presenter: P

    protected abstract fun fetchView(): V

    override fun onStart() {
        super.onStart()
        presenter.bindView(fetchView())
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        hideLoading()
    }

    override fun onStop() {
        presenter.unbindView(fetchView())
        super.onStop()
    }

    override fun showError(error: String) {
        showToast(Message.alert(error))
    }

    override fun showInfo(info: String) {
        showToast(Message.info(info))
    }

    override fun showConfirm(confirm: String) {
        showToast(Message.confirm(confirm))
    }

    override fun showLoading(info: String) {
        NpeUtils.call(dialog, Consumer<SpinnerProgressDialog> { dialog -> dialog.dismissAllowingStateLoss() })
        dialog = SpinnerProgressDialog.newInstance(info, false)
        dialog?.show(childFragmentManager, SpinnerProgressDialog::class.java.simpleName)
    }

    override fun hideLoading() {
        NpeUtils.call(dialog, Consumer<SpinnerProgressDialog> { dialog -> dialog.dismissAllowingStateLoss() })
        dialog = null
    }

    override fun onFinish() {
        NpeUtils.call(presenter, Consumer<P> { p -> p.onFinish() })
        super.onFinish()
    }
}
