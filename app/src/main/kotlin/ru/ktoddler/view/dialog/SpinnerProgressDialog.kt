package ru.ktoddler.view.dialog

import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle

import ru.ktoddler.util.NpeUtils

class SpinnerProgressDialog : BaseDialogFragment() {

    companion object {

        private val ARG_TITLE = "arg_title"
        private val ARG_MESSAGE = "arg_message"
        private val ARG_IS_CANCELABLE = "arg_is_cancelable"

        fun newInstance(message: String, isCancelable: Boolean): SpinnerProgressDialog {
            return newInstance(null, message, isCancelable)
        }

        fun newInstance(title: String?, message: String, isCancelable: Boolean): SpinnerProgressDialog {
            val dialog = SpinnerProgressDialog()
            val args = Bundle()
            args.putString(ARG_TITLE, title)
            args.putString(ARG_MESSAGE, message)
            args.putBoolean(ARG_IS_CANCELABLE, isCancelable)
            dialog.arguments = args
            return dialog
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = arguments.getBoolean(ARG_IS_CANCELABLE, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = ProgressDialog(activity, theme)
        val title = arguments.getString(ARG_TITLE, null)
        if (!NpeUtils.isEmpty(title)) {
            dialog.setTitle(title)
        }
        dialog.setMessage(arguments.getString(ARG_MESSAGE))
        dialog.isIndeterminate = true
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER)
        return dialog
    }
}
