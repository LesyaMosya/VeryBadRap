package com.example.verybadrap.hilt.impl

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.verybadrap.hilt.services.TextService
import com.example.verybadrap.model.Round
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

