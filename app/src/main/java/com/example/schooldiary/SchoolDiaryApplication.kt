package com.example.schooldiary

import android.app.Application
import androidx.room.Room
import com.example.schooldiary.data.database.AppDatabase
import com.example.schooldiary.data.database.MIGRATION_1_2

class SchoolDiaryApplication : Application() {
    val database: AppDatabase by lazy {
        Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "school_diary_db"
        )
            .addMigrations(MIGRATION_1_2)
            .build()
    }
}