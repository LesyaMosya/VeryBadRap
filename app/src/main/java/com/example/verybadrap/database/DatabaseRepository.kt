package com.example.verybadrap.database


import androidx.annotation.WorkerThread
import com.example.verybadrap.model.Song
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DatabaseRepository @Inject constructor (private val songDao: SongDao) {

    // Room executes all queries on a separate thread.
    // Observed Flow will notify the observer when the data has changed.
    fun getListOfSongs(difficult: Int, limit: Int) = songDao.getListOfSongs(difficult, limit)


    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @WorkerThread
    suspend fun upsertSong(song: Song) {
        songDao.upsertSong(song)
    }

    @WorkerThread
    suspend fun deleteSong(song: Song) {
        songDao.deleteSong((song))
    }
}