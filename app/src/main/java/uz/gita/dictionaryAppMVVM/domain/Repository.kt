package uz.gita.dictionaryAppMVVM.domain

import android.database.Cursor
import androidx.lifecycle.LiveData
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData

interface Repository {

    fun getAllEnglishWords(): Cursor

    fun getAllFavouriteEnglishWords(): Cursor

    fun getAllUzbekWords(): Cursor

    fun getAllFavouriteUzbekWords(): Cursor

    fun getAllEnglishWordsByQuery(query: String): Cursor

    fun getAllFavouriteEnglishWordsByQuery(query: String): Cursor

    fun getAllUzbekWordsByQuery(query: String): Cursor

    fun getAllFavouriteUzbekWordsByQuery(query: String): Cursor

    fun getItemById(id: Int): DialogItemData

    fun setFavourite(state: Int, id: Int)

    fun getCurrentLanguage(): Boolean

    fun setCurrentLanguage(language: Boolean)

}