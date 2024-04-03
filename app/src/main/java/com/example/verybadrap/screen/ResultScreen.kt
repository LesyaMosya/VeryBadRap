package com.example.verybadrap.screen


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.R
import com.example.verybadrap.screen.destinations.RoundScreenDestination
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ResultScreen(
    roundsViewModel: RoundsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){

    if (roundsViewModel.isEnding.value)
        OutputResult(navigator, roundsViewModel)
    else
        OutputRating(navigator, roundsViewModel)
}

@Composable
fun OutputRating(
    navigator: DestinationsNavigator,
    roundsViewModel: RoundsViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.rating),
            contentDescription = stringResource(R.string.round),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(50.dp))

        Box(modifier = Modifier.weight(5f))
        {
            OutputScore(roundsViewModel)
        }

        NextRound(navigator)
    }
}

@Composable
fun OutputScore(
    roundsViewModel: RoundsViewModel
){
    val teams = roundsViewModel.listOfTeams
    Column (
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        teams.sortByDescending { it.score }
        for (team in teams) {
            Row(
                modifier = Modifier
                    .size(350.dp, 90.dp)
                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(20.dp))
                    .border(4.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(20.dp))
                    .padding(20.dp, 5.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = team.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                    softWrap = true,
                    modifier = Modifier
                        .width(250.dp)
                )

                Text(
                    text = team.score.toString(),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                    softWrap = false
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun NextRound(
    navigator: DestinationsNavigator
) {
    Box {
        Image(
            painter = painterResource(R.drawable.next_btn),
            contentDescription = "STATE",
            modifier = Modifier
                .size(80.dp)
                .clickable
                {
                    navigator.navigate(RoundScreenDestination)
                }
        )
    }
}

@Composable
fun OutputResult(
    navigator: DestinationsNavigator,
    roundsViewModel: RoundsViewModel
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.result),
            contentDescription = stringResource(R.string.round),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(40.dp))

        Box(modifier = Modifier.weight(6f))
        {
            OutputWinner(roundsViewModel)
        }

        ReturnHome(navigator)
    }
}

@Composable
fun OutputWinner(roundsViewModel: RoundsViewModel){
    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.winner),
            contentDescription = stringResource(R.string.round),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .width(380.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

            val winner = roundsViewModel.listOfTeams.maxBy { it.score}
        Row(
            modifier = Modifier
                .size(350.dp, 90.dp)
                .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(25.dp))
                .border(4.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(25.dp))
                .padding(20.dp, 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = winner.title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                softWrap = true,
                modifier = Modifier
                    .width(250.dp)
            )

            Text(
                text = winner.score.toString(),
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onTertiary,
                softWrap = false
            )
        }
    }
}
