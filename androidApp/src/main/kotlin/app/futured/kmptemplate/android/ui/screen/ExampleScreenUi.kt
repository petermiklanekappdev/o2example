package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.futured.kmptemplate.android.ui.theme.MyApplicationTheme
import app.futured.kmptemplate.android.ui.components.Input
import app.futured.kmptemplate.android.ui.components.MainButton
import app.futured.kmptemplate.android.ui.components.PasswordInput
import app.futured.kmptemplate.android.ui.components.ScratchCard
import app.futured.kmptemplate.android.ui.theme.LocalExampleColorScheme
import app.futured.kmptemplate.android.ui.theme.SpacingM
import app.futured.kmptemplate.feature.ui.example.ExampleScreen
import app.futured.kmptemplate.feature.ui.example.ExampleViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun ExampleScreenUi(
    screen: ExampleScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsState()

    Content(
        actions = actions,
        viewState = viewState,
        modifier = modifier,
    )
}

@Composable
private fun Content(
    actions: ExampleScreen.Actions,
    viewState: ExampleViewState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar()
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(SpacingM),
            ) {
                viewState.points?.let { points ->
                    ScratchCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
                        brushSize = 48.dp,
                        overlayColor = Color.Black,
                        initialNormalizedStrokes = points,
                        onNormalizedStrokesChanged = {},
                        revealThreshold = 100f,
                        onReveal = {},
                        isRevealed = viewState.isRevealed,
                        isEnabled = false
                    ) {
                        // hidden content
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(Color.White),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = viewState.code,
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                }

                MainButton(
                    text = kmpStringResource(MR.strings.example_button_scratch),
                    onClick = actions::onScratch
                )
                MainButton(
                    text = kmpStringResource(MR.strings.example_button_activation),
                    onClick = actions::onActivation
                )
                MainButton(
                    text = kmpStringResource(MR.strings.example_button_reset_data),
                    onClick = actions::onResetData
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = kmpStringResource(MR.strings.example_title),
                style = MaterialTheme.typography.labelMedium,
                color = LocalExampleColorScheme.current.content.onNeutral.xxHigh,
            )
        }
    )
}

@Preview
@Composable
private fun ExampleScreenPreview() {
    val actions = object : ExampleScreen.Actions {
        override fun onScratch() {}
        override fun onActivation() {}
        override fun onResetData() {}
    }
    MyApplicationTheme {
        Surface {
            Content(
                actions = actions,
                viewState = ExampleViewState.EMPTY,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
