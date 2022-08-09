package uz.gita.dictionaryAppMVVM.data.model

data class DialogItemData(
    val id: Int,
    val word: String?,
    val transcript: String?,
    val translate: String?,
    val type: String?,
    val countable: String?,
    val isFavourite: Int?
)