package uz.gita.dictionaryAppMVVM.data.local.room

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "dictionary")
data class DictionaryEntity(
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "english")
    val english: String?,
    @ColumnInfo(name = "type")
    val type: String?,
    @ColumnInfo(name = "transcript")
    val transcript: String?,
    @ColumnInfo(name = "uzbek")
    val uzbek: String?,
    @ColumnInfo(name = "countable")
    val countable: String?,
    @ColumnInfo(name = "is_favourite")
    val isFavourite: Int?
)
