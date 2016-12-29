package ru.ktoddler.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.ktoddler.KToddlerApp
import ru.ktoddler.view.notification.KToddlerNotification
import ru.ktoddler.view.notification.Message
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var notifications: KToddlerNotification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as KToddlerApp).appComponent.inject(this)
    }

    override fun onResume() {
        super.onResume()
        notifications.showToast(Message.alert("Toast message"))
        notifications.showSnackbar(this, null, Message.alert("Snackbar message"))
    }
}
