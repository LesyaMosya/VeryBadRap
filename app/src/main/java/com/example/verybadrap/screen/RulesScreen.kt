package com.example.verybadrap.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verybadrap.R
import com.example.verybadrap.hilt.impl.TextServiceImpl
import com.example.verybadrap.ui.theme.VeryBadRapTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.navigation.EmptyDestinationsNavigator


@Destination
@Composable
fun RulesScreen(navigator: DestinationsNavigator) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(5.dp, 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.rules),
            contentDescription = stringResource(R.string.rules),
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutputRules()

        Spacer(modifier = Modifier.height(40.dp))

        ReturnHome(navigator)
    }
}

@Composable
fun OutputRules() {
    val textServiceImpl = TextServiceImpl(LocalContext.current)
    Surface(
        modifier = Modifier
            .size(380.dp, 450.dp),
        color = MaterialTheme.colorScheme.onSurface,
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary),

    ){
        Text(
            text = textServiceImpl.readingTextFile("rules.txt"),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onTertiary,
            softWrap = true,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(10.dp, 5.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RulesScreenPreview() {
    VeryBadRapTheme {
        RulesScreen(navigator = EmptyDestinationsNavigator)
    }
}