package ru.ktoddler.model.preference

import android.content.Context
import ru.ktoddler.BuildConfig
import ru.ktoddler.di.scope.PerApplication
import javax.inject.Inject

@PerApplication
class UiPrefs
@Inject
constructor(context: Context) : BasePrefs(context) {

    fun setFirstRun(flag: Boolean) {
        putBoolean(PrefKeys.Ui.FIRST_RUN, flag)
    }

    fun firstRun(): Boolean {
        return getBoolean(PrefKeys.Ui.FIRST_RUN, true)
    }

    fun appVersionName(): String {
        return BuildConfig.VERSION_NAME
    }
}
