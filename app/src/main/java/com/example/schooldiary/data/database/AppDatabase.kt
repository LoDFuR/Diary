package com.example.schooldiary.data.database
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.example.schooldiary.data.models.Lesson
import com.example.schooldiary.data.models.Subject

@Database(entities = [Subject::class, Lesson::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun subjectDao(): SubjectDao
    abstract fun lessonDao(): LessonDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "dnevnik_shkolnika_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}