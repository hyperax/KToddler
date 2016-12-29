package ru.ktoddler.view

import ru.ktoddler.view.activity.BaseActivity
import ru.ktoddler.view.notification.Message

class MainActivity : BaseActivity() {

    override fun onResume() {
        super.onResume()
        showToast(Message.alert("Toast message 1"))
        showSnackbar(Message.alert("Snackbar message 2"))
    }
}
