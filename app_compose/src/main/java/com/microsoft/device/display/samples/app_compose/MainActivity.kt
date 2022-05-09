package com.microsoft.device.display.samples.app_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.microsoft.device.display.samples.app_compose.ui.theme.Blue
import com.microsoft.device.display.samples.app_compose.ui.theme.ExampleTheme
import com.microsoft.device.display.samples.app_compose.ui.theme.Red
import com.microsoft.device.dualscreen.twopanelayout.TwoPaneLayout

val BackgroundColorKey = SemanticsPropertyKey<Color>("BackgroundColorKey")
var SemanticsPropertyReceiver.backgroundColor by BackgroundColorKey

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ExampleTheme {
                ExampleApp()
            }
        }
    }
}

@Composable
fun ExampleApp() {
    TwoPaneLayout(
        pane1 = { Pane1() },
        pane2 = { Pane2() },
    )
}

@Composable
fun Pane1(backgroundColor: Color = Blue) {
    Box {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .semantics { this.backgroundColor = backgroundColor }
                .align(Alignment.TopCenter),
            text = stringResource(R.string.pane1),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            text = stringResource(R.string.compose),
            style = MaterialTheme.typography.h1,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Pane2(backgroundColor: Color = Red) {
    Text(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .semantics { this.backgroundColor = backgroundColor },
        text = stringResource(R.string.pane2),
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center
    )
}