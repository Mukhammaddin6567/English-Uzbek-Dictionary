package uz.gita.dictionaryAppMVVM.data.local.room

import android.database.Cursor
import androidx.room.Dao
import androidx.room.Query
import uz.gita.dictionaryAppMVVM.data.model.DialogItemData

@Dao
interface DatabaseDao {

    //    order by english asc
    @Query(
        "select id, english as word, type, uzbek as translate, is_favourite as isFavourite " +
                "from dictionary"
    )
    fun getAllEnglishWords(): Cursor

    @Query(
        "select id, english as word, type, uzbek as translate, is_favourite as isFavourite " +
                "from dictionary where is_favourite = 1"
    )
    fun getAllFavouriteEnglishWords(): Cursor

    //    order by uzbek asc
    @Query(
        "select id, uzbek as word, type, english as translate, is_favourite as isFavourite " +
                "from dictionary"
    )
    fun getAllUzbekWords(): Cursor

    @Query(
        "select id, uzbek as word, type, english as translate, is_favourite as isFavourite " +
                "from dictionary where isFavourite = 1"
    )
    fun getAllFavoriteUzbekWords(): Cursor

    @Query(
        "select id, uzbek as word, transcript, english as translate, type, countable, is_favourite as isFavourite" +
                " from dictionary where id = :id"
    )
    fun getUzbekItemDataById(id: Int): DialogItemData

    @Query(
        "select id, english as word, transcript, uzbek as translate, type, countable, is_favourite as isFavourite" +
                " from dictionary where id = :id"
    )
    fun getEnglishItemDataById(id: Int): DialogItemData

    // best solution but room does not allow it
    /*UPDATE dictionary SET is_favourite = 0
    WHERE dictionary.id = 5;
    SELECT id, uzbek as word,
    type, english as translate,
    is_favourite as isFavourite
    FROM dictionary
    WHERE dictionary.id = 5*/

    @Query("update dictionary set is_favourite = 1 where id = :id")
    fun addToFavourite(id: Int)

    @Query("update dictionary set is_favourite = 0 where id = :id")
    fun deleteFromFavourite(id: Int)

    /*@Query("SELECT id, uzbek as word, " +
            "type, english as translate, " +
            "is_favourite as isFavourite " +
            "FROM dictionary " +
            "WHERE dictionary.id = :id"
    )
    fun getFavouriteItem(id: Int): Cursor*/

    @Query(
        "select id, english as word, type, uzbek as translate, is_favourite as isFavourite " +
                "from dictionary where dictionary.english like :query"
    )
    fun getAllEnglishWordsByQuery(query: String): Cursor

    @Query(
        "select id, english as word, type, uzbek as translate, is_favourite as isFavourite " +
                "from dictionary where dictionary.english like :query and is_favourite = 1"
    )
    fun getAllFavouriteEnglishWordsByQuery(query: String): Cursor

    @Query(
        "select id, uzbek as word, type, english as translate, is_favourite as isFavourite " +
                "from dictionary where dictionary.uzbek like :query"
    )
    fun getAllUzbekWordsByQuery(query: String): Cursor

    @Query(
        "select id, uzbek as word, type, english as translate, is_favourite as isFavourite " +
                "from dictionary where dictionary.uzbek like :query and is_favourite = 1"
    )
    fun getAllFavouriteUzbekWordsByQuery(query: String): Cursor

}