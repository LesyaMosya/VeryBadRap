package com.example.verybadrap.database

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton // Tell Dagger-Hilt to create a singleton accessible everywhere in ApplicationCompenent (i.e. everywhere in the application)
    @Provides
    fun provideAppDataBase( @ApplicationContext context: Context) : AppDatabase
    {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "very_bad_rap"
        ).createFromAsset("database/VeryBadRap.db")
            .build()
    } // The reason we can construct a database for the repo

    @Singleton
    @Provides
    fun provideSongDao(db: AppDatabase) = db.getSongDao() // The reason we can implement a Dao for the database

}