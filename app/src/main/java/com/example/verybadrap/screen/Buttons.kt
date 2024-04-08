package com.example.verybadrap.screen

import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.R
import com.example.verybadrap.screen.destinations.HomeScreenDestination
import com.example.verybadrap.viewmodel.Event
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun ReturnHome(
    navigator: DestinationsNavigator,
    roundsViewModel: RoundsViewModel = hiltViewModel()
){
    Box() {
        Image(
            painter = painterResource(R.drawable.home_btn),
            contentDescription = "HOME",
            modifier = Modifier
                .size(80.dp)
                .clickable {
                    roundsViewModel.createEvent(Event.ReturnHome)
                    navigator.navigate(HomeScreenDestination)
                }
        )
    }
}

fun displayToast(context: Context) {
    Toast.makeText(context, R.string.warning, Toast.LENGTH_SHORT).show()
}

@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}