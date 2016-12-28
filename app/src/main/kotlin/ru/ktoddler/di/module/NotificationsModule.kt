package ru.ktoddler.di.module

import android.content.Context
import dagger.Module
import ru.ktoddler.view.notification.Message

@Module
abstract class NotificationsModule(context: Context) {

    init {
        Message.init(context)
    }
}