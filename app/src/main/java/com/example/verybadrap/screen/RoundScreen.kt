package com.example.verybadrap.screen


import android.media.MediaPlayer
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.verybadrap.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.verybadrap.screen.destinations.ResultScreenDestination
import com.example.verybadrap.ui.theme.Brown
import com.example.verybadrap.ui.theme.YellowLight
import com.example.verybadrap.viewmodel.Event
import com.example.verybadrap.viewmodel.RoundsViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination
@Composable
fun RoundScreen(
    navigator: DestinationsNavigator,
    roundsViewModel: RoundsViewModel = hiltViewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (roundsViewModel.currentRound.value.difficult != 0) {
            LoadRound(navigator, roundsViewModel)
        } else if (!roundsViewModel.isBeginning){
            roundsViewModel.createEvent(Event.StartGame)
            LoadingScreen()
        } else {
            roundsViewModel.createEvent(Event.NextRound)
            LoadingScreen()
        }
    }
}


@Composable
fun LoadRound(
    navigator: DestinationsNavigator,
    roundsViewModel: RoundsViewModel
) {

    var painterRound = painterResource(R.drawable.round_1)
    when (roundsViewModel.currentRound.value.difficult) {
        2 -> painterRound = painterResource(R.drawable.round_2)
        3 -> painterRound = painterResource(R.drawable.round_3)
    }
    Image(
        painter = painterRound,
        contentDescription = stringResource(R.string.round),
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
    )

    OutputTeam(roundsViewModel)

    Spacer(modifier = Modifier.height(15.dp))

    val stateMusicButton = remember { mutableIntStateOf(0) }
    val stateChecking = remember { mutableStateOf(false) }
    InsertAudio(roundsViewModel, stateMusicButton, stateChecking)

    Spacer(modifier = Modifier.height(20.dp))

    val gameBtn = remember { mutableIntStateOf(R.drawable.blocked_ready_btn) }
    val enteredText = remember { mutableStateOf("") }

    if (!stateChecking.value) {
        if (enteredText.value.isNotEmpty()) {
            gameBtn.intValue = R.drawable.ready_btn
        } else {
            gameBtn.intValue = R.drawable.blocked_ready_btn
        }
        InputText(roundsViewModel, enteredText)
    } else {
        val answerSheet = remember { mutableMapOf(0 to -1) }
        CheckingText(roundsViewModel, enteredText, answerSheet)
        stateMusicButton.intValue = 0
    }

    Spacer(modifier = Modifier.height(15.dp))

    ClickableButton(stateChecking, gameBtn, navigator)

    val context = LocalContext.current
    val onBack = {
        displayToast(context)
    }
    BackPressHandler(onBackPressed = onBack)

}

@Composable
fun OutputTeam(roundsViewModel: RoundsViewModel) {

    val team = roundsViewModel.currentTeam.value
    if (team.title != "Количество баллов") {
        Spacer(modifier = Modifier.height(25.dp))
        Text(
            text = team.title,
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onTertiary,
            softWrap = true,
            modifier = Modifier
                .width(380.dp),
            textAlign = TextAlign.Center
        )
    }
    else {
        Spacer(modifier = Modifier.height(60.dp))
    }
}

@Composable
fun InsertAudio(
    roundsViewModel: RoundsViewModel,
    stateMusicButton : MutableIntState,
    stateChecking: MutableState<Boolean>
){
    val context = LocalContext.current

    val titleAudio = roundsViewModel.currentSong.value.title
    val audioID = context.resources.getIdentifier(titleAudio, "raw", context.packageName)

    val mMediaPlayer: MediaPlayer = remember {
        MediaPlayer.create(context, audioID)
    }

    val flag = remember { mutableStateOf(true) }
    SideEffect {
        if (stateChecking.value && flag.value ) {
            mMediaPlayer.stop()
            mMediaPlayer.prepare()
            flag.value = false
        }
    }


        Box {
        Image(
            painter = if (stateMusicButton.intValue < 3 || stateChecking.value) painterResource(R.drawable.play_music_btn)
            else painterResource(R.drawable.blocked_music_btn),
            contentDescription = "PLAY",
            modifier = Modifier
                .size(80.dp)
                .clickable {
                    if (mMediaPlayer.isPlaying) {
                        mMediaPlayer.stop()
                        mMediaPlayer.prepare()
                    }
                    if (stateMusicButton.intValue < 3 || stateChecking.value) {
                        stateMusicButton.intValue++
                        mMediaPlayer.start()
                    }
                }
        )
    }

}

@Composable
fun InputText(
    roundsViewModel: RoundsViewModel,
    enteredText: MutableState<String>
){
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val maxChar = 120
    OutlinedTextField(
        value = enteredText.value,
        onValueChange = {
            if (it.length <= maxChar) enteredText.value = it
            else Toast.makeText(context, R.string.limit_word, Toast.LENGTH_SHORT).show()
        },
        interactionSource = interactionSource,
        enabled = true,
        singleLine = false,
        maxLines = roundsViewModel.computeMaxLines(),
        textStyle = MaterialTheme.typography.displayMedium,
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = YellowLight,
            focusedContainerColor = YellowLight,
            cursorColor = Brown,
            focusedTextColor = Brown,
            disabledContainerColor = YellowLight,
            disabledTextColor = Brown
        ),
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .size(380.dp, 290.dp)
            .border(BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary), RoundedCornerShape(15.dp)),
/*        supportingText = {
            Text(
                text = "${enteredText.value.length} / $maxChar",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.End,
                style = MaterialTheme.typography.labelSmall,
                color = Brown
            )
        },*/
    )
}

@Composable
fun CheckingText(
    roundsViewModel: RoundsViewModel,
    enteredText: MutableState<String>,
    answerSheet : MutableMap<Int, Int>
){

    Surface(
        modifier = Modifier
            .heightIn(270.dp, 330.dp)
            .width(380.dp),
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(15.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary),
    ){

        if (answerSheet[0] == -1) {
            answerSheet[0] = 0
            answerSheet.putAll(roundsViewModel.checkingText(enteredText.value))
        }
        val textSong = roundsViewModel.currentTextOfSong.value
        var text = ""
        var i = 0

        Text(
            text = buildAnnotatedString {
                while (i < textSong.length) {

                    var lengthOfWord = answerSheet[i]
                    if (lengthOfWord != null && lengthOfWord != 0) {
                        for (letterIndex in 1..lengthOfWord) {
                            text += textSong[i]
                            i++
                        }

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        ) {
                            append(text)
                        }
                        text = ""
                        i.plus(lengthOfWord)

                    } else {
                        while ((lengthOfWord == null || lengthOfWord == 0) && i < textSong.length) {
                            text += textSong[i]
                            i++
                            lengthOfWord = answerSheet[i]
                        }

                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.onTertiary
                            )
                        ) {
                            append(text)
                        }

                        text = ""
                    }
                }
            },
            style = MaterialTheme.typography.displayMedium,
            softWrap = true,
            modifier = Modifier
                .padding(15.dp, 7.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}

@Composable
fun ClickableButton(
    stateChecking: MutableState<Boolean>,
    picture: MutableIntState,
    navigator: DestinationsNavigator
){
    Box {
        Image(
            painter = painterResource(picture.intValue),
            contentDescription = "STATE",
            modifier = Modifier
                .size(80.dp)
                .clickable
                {
                    if (!stateChecking.value) {
                        stateChecking.value = true
                        picture.intValue = R.drawable.next_btn
                    } else {
                        navigator.navigate(ResultScreenDestination)
                    }
                }
        )
    }
}


/*@Preview(showBackground = true, apiLevel = 33)
@Composable
fun PreviewInputText(){
    VeryBadRapTheme {
        val enteredText  = remember {
            mutableStateOf("")
        }
        InputText(enteredText)
    }
}*/
