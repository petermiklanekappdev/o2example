package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import app.futured.kmptemplate.android.ui.components.MainButton
import app.futured.kmptemplate.android.ui.components.ScratchCard
import app.futured.kmptemplate.android.ui.theme.LocalExampleColorScheme
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreen
import app.futured.kmptemplate.feature.ui.scratch.ScratchViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@Composable
fun ScratchScreenUi(
    screen: ScratchScreen,
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
    actions: ScratchScreen.Actions,
    viewState: ScratchViewState,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(actions)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                viewState.points?.let { points ->
                    ScratchCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                            .height(200.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, Color.Black, RoundedCornerShape(16.dp)),
                        brushSize = 48.dp,
                        overlayColor = Color.Black,
                        initialNormalizedStrokes = points,
                        onNormalizedStrokesChanged = actions::onScratchChange,
                        revealThreshold = 100f,
                        onReveal = actions::onRevealed,
                        isRevealed = viewState.isRevealed
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
                    modifier = Modifier.padding(horizontal = 16.dp),
                    text = kmpStringResource(MR.strings.scratch_reveal),
                    onClick = actions::onReveal,
                    loading = viewState.isLoading
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    actions: ScratchScreen.Actions,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = kmpStringResource(MR.strings.scratch_title),
                style = MaterialTheme.typography.labelMedium,
                color = LocalExampleColorScheme.current.content.onNeutral.xxHigh,
            )
        },
        navigationIcon = {
            IconButton(onClick = { actions.onPop() }) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
            }
        },
    )
}

@Preview
@Composable
private fun Preview() {
    val actions = object : ScratchScreen.Actions {
        override fun onReveal() {}
        override fun onRevealed() {}
        override fun onPop() {}
        override fun onScratchChange(value: List<List<ScratchPoint>>) {}
    }
    MyApplicationTheme {
        Surface {
            Content(
                actions = actions,
                viewState = ScratchViewState.EMPTY,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
