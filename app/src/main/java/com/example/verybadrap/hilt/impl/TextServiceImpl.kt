package com.example.verybadrap.hilt.impl

import android.content.Context
import com.example.verybadrap.hilt.services.TextService
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class TextServiceImpl @Inject constructor (
    @ApplicationContext private val context: Context
): TextService {

    override fun readingTextFile(source: String) : String {
        return context.assets.open(source).bufferedReader().use {
            it.readText()
        }
    }
}

