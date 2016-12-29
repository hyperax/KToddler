package ru.ktoddler.presenter

import ru.ktoddler.view.View

interface Presenter<in V : View> {

    fun bindView(view: V)

    fun unbindView(view: V)

    fun onFinish()
}
