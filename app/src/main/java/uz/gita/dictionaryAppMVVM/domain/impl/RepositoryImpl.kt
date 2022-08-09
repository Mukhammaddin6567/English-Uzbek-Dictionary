package uz.gita.dictionaryAppMVVM.domain.impl

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import uz.gita.dictionaryAppMVVM.data.local.AppPreferences
import uz.gita.dictionaryAppMVVM.data.local.room.AppDatabase
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData
import uz.gita.dictionaryAppMVVM.domain.Repository

class RepositoryImpl : Repository {

    private val database: AppDatabase by lazy { AppDatabase.getDatabase() }
    private val preferences: AppPreferences by lazy { AppPreferences.getAppPreferences() }

    override fun getAllEnglishWords(): Cursor =
        database.dao().getAllEnglishWords()

    override fun getAllFavouriteEnglishWords(): Cursor =
        database.dao().getAllFavouriteEnglishWords()

    override fun getAllUzbekWords(): Cursor =
        database.dao().getAllUzbekWords()

    override fun getAllFavouriteUzbekWords(): Cursor =
        database.dao().getAllFavoriteUzbekWords()

    override fun getAllEnglishWordsByQuery(query: String): Cursor =
        database.dao().getAllEnglishWordsByQuery(query)

    override fun getAllFavouriteEnglishWordsByQuery(query: String): Cursor =
        database.dao().getAllFavouriteEnglishWordsByQuery(query)

    override fun getAllUzbekWordsByQuery(query: String): Cursor =
        database.dao().getAllUzbekWordsByQuery(query)

    override fun getAllFavouriteUzbekWordsByQuery(query: String): Cursor =
        database.dao().getAllFavouriteUzbekWordsByQuery(query)

    override fun getItemById(id: Int): DialogItemData {
        return when (preferences.currentLanguage) {
            false -> {
                Timber.d(database.dao().getEnglishItemDataById(id).toString())
                database.dao().getEnglishItemDataById(id)
            }
            else -> {
                Timber.d(database.dao().getUzbekItemDataById(id).toString())
                database.dao().getUzbekItemDataById(id)
            }
        }
    }

    override fun setFavourite(state: Int, id: Int) {
        Timber.d("setFavourite state: $state")
        Timber.d("setFavourite id: $id")
        when (state) {
            0 -> database.dao().addToFavourite(id)
            else -> database.dao().deleteFromFavourite(id)
        }
    }

    override fun getCurrentLanguage(): Boolean = preferences.currentLanguage

    override fun setCurrentLanguage(language: Boolean) {
        preferences.currentLanguage = language
    }
}