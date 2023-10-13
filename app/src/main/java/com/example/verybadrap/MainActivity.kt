package com.example.verybadrap

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.verybadrap.ui.theme.VeryBadRapTheme
import kotlin.system.exitProcess

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            VeryBadRapTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.onBackground
                ) {
                    Image (
                        painter = painterResource(R.drawable.title),
                        contentDescription = stringResource(R.string.app_name),
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.TopCenter,
                        modifier = Modifier.offset(y=10.dp)
                    )
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.offset(y=25.dp)
                    ) {
                        ButtonMenu(nameBtn = stringResource(R.string.start_btn), onClick = {})
                        ButtonMenu(nameBtn = stringResource(R.string.rules_btn), onClick = {})
                        ButtonMenu(nameBtn = stringResource(R.string.exit_btn), onClick = {finishAffinity();})
                    }
                }
            }
        }
    }
}

@Composable
fun ButtonMenu(nameBtn: String, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(20.dp))
    ElevatedButton (
        onClick = { onClick() },
        shape = RoundedCornerShape(17.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onSurface,
            contentColor = MaterialTheme.colorScheme.onTertiary),
        border = BorderStroke(4.dp, MaterialTheme.colorScheme.onTertiary),
        modifier = Modifier
            .width(330.dp)
    ) {
        Text(
            text = nameBtn,
/*            color = MaterialTheme.colorScheme.onTertiary,*/
            modifier = Modifier.padding(vertical = 7.dp, horizontal = 7.dp),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
fun MenuButtonPreview() {
    VeryBadRapTheme {
        ButtonMenu("Start", onClick = {})
    }
}