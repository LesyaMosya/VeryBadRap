package com.example.verybadrap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.verybadrap.screen.NavGraphs
import com.example.verybadrap.ui.theme.VeryBadRapTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VeryBadRapTheme {
                Surface {
                    DestinationsNavHost( navGraph = NavGraphs.root )
                }
            }
        }
    }
}



