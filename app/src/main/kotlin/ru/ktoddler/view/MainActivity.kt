package ru.ktoddler.view

import android.os.Bundle
import ru.ktoddler.model.entity.KToddlerEntity
import ru.ktoddler.model.repository.LocalRepository
import ru.ktoddler.view.activity.BaseActivity
import ru.ktoddler.view.notification.Message
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var localRepo: LocalRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        provideAppComponent().inject(this)
    }

    override fun onResume() {
        super.onResume()
//        showToast(Message.alert("Toast message 3"))
//        showSnackbar(Message.alert("Snackbar message 4"))

        val toddler = KToddlerEntity()
        toddler.age = Random().nextInt()
        toddler.name = "Vasya " + toddler.age

        localRepo.put(toddler)

        localRepo.get(KToddlerEntity::class.java)
                .forEach { showToast(Message.confirm(it.name)) }
    }
}
