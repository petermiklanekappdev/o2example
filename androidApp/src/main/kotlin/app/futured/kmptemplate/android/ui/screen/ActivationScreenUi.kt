package app.futured.kmptemplate.android.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import app.futured.arkitekt.decompose.event.EventsEffect
import app.futured.arkitekt.decompose.event.onEvent
import app.futured.kmptemplate.android.ui.theme.MyApplicationTheme
import app.futured.kmptemplate.android.ui.components.Input
import app.futured.kmptemplate.android.ui.components.MainButton
import app.futured.kmptemplate.android.ui.components.PasswordInput
import app.futured.kmptemplate.android.ui.theme.LocalExampleColorScheme
import app.futured.kmptemplate.android.ui.theme.SpacingM
import app.futured.kmptemplate.feature.ui.activation.ActivationEvent
import app.futured.kmptemplate.feature.ui.activation.ActivationScreen
import app.futured.kmptemplate.feature.ui.activation.ActivationViewState
import app.futured.kmptemplate.feature.ui.example.ExampleScreen
import app.futured.kmptemplate.feature.ui.example.ExampleViewState
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreen
import app.futured.kmptemplate.feature.ui.scratch.ScratchViewState
import app.futured.kmptemplate.resources.MR
import app.futured.kmptemplate.resources.kmpStringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivationScreenUi(
    screen: ActivationScreen,
    modifier: Modifier = Modifier,
) {
    val actions = screen.actions
    val viewState by screen.viewState.collectAsState()

    var showErrorDialog by rememberSaveable { mutableStateOf(false) }
    var showSuccessDialog by rememberSaveable { mutableStateOf(false) }

    EventsEffect(eventsFlow = screen.events) {
        onEvent<ActivationEvent.ShowErrorDialog> { event ->
            showErrorDialog = true
        }
        onEvent<ActivationEvent.ShowSuccessDialog> { event ->
            showSuccessDialog = true
        }
    }

    if (showErrorDialog) {
        AlertDialog(
            title = { Text(kmpStringResource(MR.strings.activation_error)) },
            onDismissRequest = { showErrorDialog = false },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text(kmpStringResource(MR.strings.activation_ok))
                }
            }
        )
    }

    if (showSuccessDialog) {
        AlertDialog(
            title = { Text(kmpStringResource(MR.strings.activation_activated)) },
            onDismissRequest = { showSuccessDialog = false },
            confirmButton = {
                TextButton(onClick = { showSuccessDialog = false }) {
                    Text(kmpStringResource(MR.strings.activation_ok))
                }
            }
        )
    }

    Content(
        actions = actions,
        viewState = viewState,
        modifier = modifier,
    )
}

@Composable
private fun Content(
    actions: ActivationScreen.Actions,
    viewState: ActivationViewState,
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
                    .verticalScroll(rememberScrollState())
                    .imePadding()
                    .padding(SpacingM),
            ) {
                MainButton(
                    text = kmpStringResource(MR.strings.activation_activate),
                    onClick = actions::onActivate,
                    loading = viewState.isLoading
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopAppBar(
    actions: ActivationScreen.Actions,
    modifier: Modifier = Modifier
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = kmpStringResource(MR.strings.activation_title),
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
    val actions = object : ActivationScreen.Actions {
        override fun onActivate() {}
        override fun onPop() {}
    }
    MyApplicationTheme {
        Surface {
            Content(
                actions = actions,
                viewState = ActivationViewState.EMPTY,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }
}
