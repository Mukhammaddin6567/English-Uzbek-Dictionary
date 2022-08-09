package uz.gita.dictionaryAppMVVM.data.local

import android.content.Context
import android.content.SharedPreferences
import uz.gita.dictionaryAppMVVM.utils.PreferenceKeys

class AppPreferences {

    companion object {
        private var preferences: SharedPreferences? = null
        private var myPreferences: AppPreferences? = null

        fun init(context: Context) {
            myPreferences = AppPreferences()
            preferences = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
        }

        fun getAppPreferences(): AppPreferences {
            return myPreferences!!
        }
    }

    var currentLanguage: Boolean
        get() = preferences!!.getBoolean(
            PreferenceKeys.CURRENT_LANGUAGE.key,
            false
        )
        set(value) = preferences!!.edit().putBoolean(PreferenceKeys.CURRENT_LANGUAGE.key, value)
            .apply()
}