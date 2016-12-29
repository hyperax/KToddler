package ru.ktoddler.model.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import ru.ktoddler.util.DateUtils
import java.util.concurrent.TimeUnit

abstract class BasePrefs(protected val context: Context) {

    companion object {
        private val PREFERENCE_CHANGES_TIMEOUT: Long = 100
    }

    private val prefs: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    private val changesBus = PublishSubject.create<String>()

    private fun onPreferenceChanged(prefName: String) {
        changesBus.onNext(prefName)
    }

    fun changes(prefName: String): Observable<Long> {
        return changesBus.hide()
                .filter { s -> s.equals(prefName, ignoreCase = true) }
                .map { DateUtils.getCurrentMillis() }
                .debounce(PREFERENCE_CHANGES_TIMEOUT, TimeUnit.MILLISECONDS)
    }

    protected val editor: SharedPreferences.Editor
        get() = prefs.edit()

    protected fun getString(key: String, defaultVal: String): String? {
        return prefs.getString(key, defaultVal)
    }

    protected fun putString(key: String, value: String?) {
        editor.putString(key, value).apply()
        onPreferenceChanged(key)
    }

    protected fun getStringSet(key: String, defaultVal: Set<String>): Set<String>? {
        return prefs.getStringSet(key, defaultVal)
    }

    protected fun putStringSet(key: String, value: Set<String>?) {
        editor.putStringSet(key, value).apply()
        onPreferenceChanged(key)
    }

    protected fun getInt(key: String, defaultVal: Int): Int {
        return prefs.getInt(key, defaultVal)
    }

    protected fun putInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
        onPreferenceChanged(key)
    }

    protected fun getLong(key: String, defaultVal: Long): Long {
        return prefs.getLong(key, defaultVal)
    }

    protected fun putLong(key: String, value: Long) {
        editor.putLong(key, value).apply()
        onPreferenceChanged(key)
    }

    protected fun getFloat(key: String, defaultVal: Float): Float {
        return prefs.getFloat(key, defaultVal)
    }

    protected fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value).apply()
        onPreferenceChanged(key)
    }

    protected fun getBoolean(key: String, defaultVal: Boolean): Boolean {
        return prefs.getBoolean(key, defaultVal)
    }

    protected fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
        onPreferenceChanged(key)
    }

}
