package ru.ktoddler.view.behaviour

interface CallbackProvider {
    fun <T> getCallback(callbackClass: Class<T>): T?
}
