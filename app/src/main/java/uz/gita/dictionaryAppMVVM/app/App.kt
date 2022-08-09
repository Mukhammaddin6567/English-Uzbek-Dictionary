package uz.gita.dictionaryAppMVVM.app

import android.app.Application
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.data.local.AppPreferences
import uz.gita.dictionaryAppMVVM.data.local.room.AppDatabase

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        // Timber
        val checkDebug = true
        if (checkDebug) Timber.plant(Timber.DebugTree())
        Timber.d("onCreate()")

        AppPreferences.init(this)
        AppDatabase.init(this)
    }

}