package com.example.verybadrap.screen

import android.app.Activity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.R
import com.example.verybadrap.screen.destinations.RoundScreenDestination
import com.example.verybadrap.screen.destinations.RulesScreenDestination
import com.example.verybadrap.screen.destinations.TeamsScreenDestination
import com.example.verybadrap.ui.theme.VeryBadRapTheme
import com.example.verybadrap.viewmodel.Event
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(navigator: DestinationsNavigator){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image (
            painter = painterResource(R.drawable.title),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(30.dp))

        ButtonMenu(
            nameBtn = stringResource(R.string.start),
            onClick = {
                navigator.navigate(TeamsScreenDestination)
            }
        )

        StartTraining(navigator)

        ButtonMenu(
            nameBtn = stringResource(R.string.rules),
            onClick = {
                navigator.navigate(RulesScreenDestination)
            }
        )

        val activity = (LocalContext.current as? Activity)
        ButtonMenu(
            nameBtn = stringResource(R.string.exit_btn),
            onClick = {activity?.finish()}
        )
    }
}

@Composable
fun ButtonMenu(
    nameBtn: String,
    onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(20.dp))
    ElevatedButton (
        onClick = { onClick() },
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.onTertiary),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary),
        modifier = Modifier
            .width(340.dp)
    ) {
        Text(
            text = nameBtn,
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 5.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun StartTraining(
    navigator : DestinationsNavigator,
    roundsViewModel : RoundsViewModel = hiltViewModel()
){
    Spacer(modifier = Modifier.height(20.dp))

    ElevatedButton (
        onClick =
        {
            roundsViewModel.createEvent(Event.StartGame)
            navigator.navigate(RoundScreenDestination)
        },
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.onTertiary),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary),
        modifier = Modifier
            .width(340.dp)
    ) {
        Text(
            text = stringResource(R.string.training),
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 5.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    VeryBadRapTheme {
        HomeScreen(navigator = EmptyDestinationsNavigator)
    }
}