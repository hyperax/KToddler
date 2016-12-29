package ru.ireca.guest.view.notification

import android.support.design.widget.Snackbar

enum class Duration(val type: Int) {
    SHORT(Snackbar.LENGTH_SHORT),
    LONG(Snackbar.LENGTH_LONG),
    INDEFINITE(Snackbar.LENGTH_INDEFINITE)
}