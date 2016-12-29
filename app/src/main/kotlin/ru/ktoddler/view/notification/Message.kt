package ru.ktoddler.view.notification


import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.StringRes
import ru.ireca.guest.view.notification.Duration
import ru.ireca.guest.view.notification.Type
import ru.ktoddler.util.DateUtils

class Message private constructor() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var ctx: Context? = null

        fun init(context: Context) {
            ctx = context.applicationContext
        }

        fun get(): Message {
            return Message()
        }

        operator fun get(text: String): Message {
            return get().setText(text)
        }

        operator fun get(@StringRes resId: Int): Message {
            return get().setText(resId)
        }

        fun info(): Message {
            return get().setType(Type.INFO)
        }

        fun info(text: String): Message {
            return info().setText(text)
        }

        fun info(@StringRes resId: Int): Message {
            return info().setText(resId)
        }

        fun alert(): Message {
            return get().setType(Type.ALERT)
        }

        fun alert(text: String): Message {
            return alert().setText(text)
        }

        fun alert(@StringRes resId: Int): Message {
            return alert().setText(resId)
        }

        fun confirm(): Message {
            return get().setType(Type.CONFIRM)
        }

        fun confirm(text: String): Message {
            return confirm().setText(text)
        }

        fun confirm(@StringRes resId: Int): Message {
            return confirm().setText(resId)
        }
    }

    val createonDateMillis: Long = DateUtils.getCurrentMillis()

    private var type: Type = Type.INFO

    private var duration: Duration = Duration.SHORT

    private var text: String = ""

    init {
        setType(Type.INFO)
        setDuration(Duration.SHORT)
    }

    fun getType(): Type {
        return type
    }

    fun setType(type: Type): Message {
        this.type = type
        return this
    }

    fun getDuration(): Duration {
        return duration
    }

    fun setDuration(duration: Duration): Message {
        this.duration = duration
        return this
    }

    fun getText(): String {
        return text
    }

    fun setText(text: String): Message {
        this.text = text
        return this
    }

    fun setText(@StringRes resId: Int): Message {
        this.text = getString(resId)
        return this
    }

    private fun getString(@StringRes resId: Int): String {
        return ctx?.getString(resId)?:""
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Message

        if (type != other.type) return false
        if (duration != other.duration) return false
        if (text != other.text) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + duration.hashCode()
        result = 31 * result + text.hashCode()
        return result
    }
}
