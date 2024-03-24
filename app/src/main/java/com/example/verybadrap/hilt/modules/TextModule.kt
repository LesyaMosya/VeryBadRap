package com.example.verybadrap.hilt.modules

import com.example.verybadrap.hilt.services.TextService
import com.example.verybadrap.hilt.impl.TextServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class TextModule {

    @Binds
    abstract fun bindTextService(impl: TextServiceImpl) : TextService

}