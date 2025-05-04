package com.example.schooldiary

import android.app.Application
import androidx.room.Room
import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.data.database.MIGRATION_1_2
import com.example.schooldiary.data.database.MIGRATION_2_3

class SchoolDiaryApplication : Application() {
    lateinit var database: AppDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "school_diary_db"
        )
            .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
            .build()
    }
}