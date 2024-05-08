package com.example.verybadrap.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.example.verybadrap.model.Song
import kotlinx.coroutines.flow.Flow

@Dao
interface SongDao {

    @Upsert(Song::class)
    suspend fun upsertSong(vararg song: Song)

    @Delete
    suspend fun deleteSong(song: Song)

    @Query("SELECT * FROM Song")
    fun getAllSongs() : List<Song>

    @Query("SELECT * FROM Song WHERE difficult= :difficult ORDER BY RANDOM() LIMIT :limit")
    fun getListOfSongs(difficult: Int, limit: Int): Flow<List<Song>>
}