package com.example.schooldiary.data.database

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // Create temporary subjects table
        db.execSQL("""
            CREATE TABLE subjects_temp (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                quarter INTEGER NOT NULL
            )
        """.trimIndent())
        db.execSQL("""
            INSERT INTO subjects_temp (id, name, quarter)
            SELECT id, name, quarter FROM subjects
        """.trimIndent())
        db.execSQL("DROP TABLE subjects")
        db.execSQL("ALTER TABLE subjects_temp RENAME TO subjects")

        // Create temporary lessons table
        db.execSQL("""
            CREATE TABLE lessons_temp (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subjectId INTEGER NOT NULL,
                dayOfWeek INTEGER NOT NULL,
                homework TEXT,
                grade INTEGER,
                FOREIGN KEY(subjectId) REFERENCES subjects(id) ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("""
            INSERT INTO lessons_temp (id, subjectId, dayOfWeek, homework, grade)
            SELECT id, subjectId, dayOfWeek, homework, grade FROM lessons
        """.trimIndent())
        db.execSQL("DROP TABLE lessons")
        db.execSQL("ALTER TABLE lessons_temp RENAME TO lessons")

        // Create grades table
        db.execSQL("""
            CREATE TABLE grades (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                subjectId INTEGER NOT NULL,
                value INTEGER NOT NULL,
                date TEXT NOT NULL,
                FOREIGN KEY(subjectId) REFERENCES subjects(id) ON DELETE CASCADE
            )
        """.trimIndent())
    }
}