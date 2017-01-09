package ru.ktoddler.model.exception

class KToddlerException(val title: String?, val details: String?) :
        RuntimeException(title + ": " + details) {
}
