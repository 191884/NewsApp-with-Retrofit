package com.yo.newsapp.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yo.newsapp.model.Article

@Database(entities = [Article::class], version = 3)

@TypeConverters(Converters::class)
abstract class ArticleDatabase: RoomDatabase() {
    abstract fun getArticleDao(): ArticleDAO

    companion object{
        @Volatile

//        private var INSTANCE: ArticleDatabase? = null
//
//        fun getDatabase(context: Context): ArticleDatabase {
//            // if the INSTANCE is not null, then return it,
//            // if it is, then create the database
//            return INSTANCE ?: synchronized(this) {
//                val instance = Room.databaseBuilder(
//                    context.applicationContext,
//                    ArticleDatabase::class.java,
//                    "articles_db.db"
//                ).build()
//                INSTANCE = instance
//                // return instance
//                instance
//            }
//        }
        private var articleDbInstance: ArticleDatabase? = null
        private var LOCK = Any()
        operator fun invoke(context: Context) = articleDbInstance ?: synchronized(LOCK){
            articleDbInstance ?: createDatabaseInstance(context).also {
                articleDbInstance = it
            }
        }

        private fun createDatabaseInstance(context: Context) =
            Room.databaseBuilder(
                context,
                ArticleDatabase::class.java,
                "articles_db.db"
            ).fallbackToDestructiveMigration().build()
    }
}