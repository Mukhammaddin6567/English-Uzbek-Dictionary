package uz.gita.dictionaryAppMVVM.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DictionaryEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun dao(): DatabaseDao

    companion object {
        private lateinit var instance: AppDatabase

        fun init(context: Context) {
            if (::instance.isInitialized) return
            instance = Room.databaseBuilder(context, AppDatabase::class.java, context.packageName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .createFromAsset("dictionary.db")
                .build()
        }

        fun getDatabase(): AppDatabase = instance
    }

}