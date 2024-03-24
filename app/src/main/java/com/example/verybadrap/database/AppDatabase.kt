package com.example.verybadrap.database

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.verybadrap.model.Song


@Database(entities = [Song::class], version = 1, exportSchema = false/*, autoMigrations = [AutoMigration(from = 1, to = 2)]*/)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getSongDao() : SongDao

}