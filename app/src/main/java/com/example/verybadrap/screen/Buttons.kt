package com.example.verybadrap.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
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