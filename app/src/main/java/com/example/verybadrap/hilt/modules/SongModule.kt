package com.example.verybadrap.hilt.modules

import com.example.verybadrap.hilt.services.SongService
import com.example.verybadrap.hilt.impl.SongServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class SongModule {

    @Binds
    abstract fun bindSongService(impl: SongServiceImpl): SongService

}