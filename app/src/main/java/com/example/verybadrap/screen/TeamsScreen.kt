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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.viewmodel.Event
import com.example.verybadrap.R
import com.example.verybadrap.model.Team
import com.example.verybadrap.screen.destinations.RoundScreenDestination
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import javax.annotation.Nullable

@Destination
@Composable
fun TeamsScreen(
    roundsViewModel: RoundsViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Image (
            painter = painterResource(R.drawable.teams),
            contentDescription = stringResource(R.string.teams),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.weight(6f)){
            LoadTeams(roundsViewModel)
        }

        Box (
            modifier = Modifier
                .padding(0.dp, 10.dp)
        ) {
            Row {
                ReturnHome(navigator)
                Spacer(modifier = Modifier.width(70.dp))
                NextButton(navigator)
            }
        }
    }
}

@Composable
fun LoadTeams(
    roundsViewModel: RoundsViewModel
) {
    Column {
        AddTeam(roundsViewModel)

        Spacer(modifier = Modifier.height(30.dp))

        Box {
            OutputTeams(roundsViewModel)
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun AddTeam(
    roundsViewModel: RoundsViewModel
) {
    val listTitle = listOf("ОЗОРНЫЕ БЕЛКИ", "ДИКИЕ ВОЛКИ", "СМЕШАРИКИ", "ФИКСИКИ", "ГУАПЧИЧИ")
    var i = 0

    Box(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(15.dp))
            .border(4.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(15.dp))
            .padding(10.dp, 5.dp)
            .size(350.dp, 80.dp)
            .clickable
            {
                roundsViewModel.createEvent(Event.AddTeam(listTitle[i]))
                if (i < 5) i++
                else i=0
            },
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.add_team_btn),
            contentDescription = "NEXT",
            modifier = Modifier
                .size(60.dp)
        )
    }
}

@Composable
fun OutputTeams(
    roundsViewModel: RoundsViewModel
) {
    Column (
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ) {
        for (team in roundsViewModel.listOfTeams) {
            Row(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.onSurface, RoundedCornerShape(15.dp))
                    .border(4.dp, MaterialTheme.colorScheme.onTertiary, RoundedCornerShape(15.dp))
                    .padding(15.dp, 5.dp)
                    .size(340.dp, 80.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = team.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onTertiary,
                    modifier = Modifier
                        .width(270.dp)
                )

                val painter = remember { mutableIntStateOf(R.drawable.delete_team_btn) }
                Box {
                    Image(
                        painter = painterResource(painter.intValue),
                        contentDescription = "STATE",
                        modifier = Modifier
                            .size(55.dp)
                            .clickable
                            {
                                roundsViewModel.createEvent(Event.DeleteTeam(team.title))
                            }
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

@Composable
fun NextButton( navigator: DestinationsNavigator){
    Box {
        Image(
            painter = painterResource(R.drawable.next_btn),
            contentDescription = "NEXT",
            modifier = Modifier
                .size(80.dp)
                .clickable
                {
                    navigator.navigate(RoundScreenDestination)
                }
        )
    }
}