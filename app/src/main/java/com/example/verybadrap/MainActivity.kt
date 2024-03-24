package com.example.verybadrap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.screen.NavGraphs
import com.example.verybadrap.screen.destinations.RoundScreenDestination
import com.example.verybadrap.ui.theme.VeryBadRapTheme
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.manualcomposablecalls.composable
import com.ramcosta.composedestinations.navigation.dependency
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



