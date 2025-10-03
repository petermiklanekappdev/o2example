package app.futured.kmptemplate.android.ui.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.futured.kmptemplate.android.ui.screen.ActivationScreenUi
import app.futured.kmptemplate.android.ui.screen.ExampleScreenUi
import app.futured.kmptemplate.android.ui.screen.ScratchScreenUi
import app.futured.kmptemplate.feature.navigation.root.RootChild
import app.futured.kmptemplate.feature.navigation.root.RootConfig
import app.futured.kmptemplate.feature.navigation.root.RootNavHost
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.androidPredictiveBackAnimatable
import com.arkivanov.decompose.extensions.compose.stack.animation.predictiveback.predictiveBackAnimation
import com.arkivanov.decompose.router.stack.ChildStack

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootNavHostUi(
    navHost: RootNavHost,
    modifier: Modifier = Modifier,
) {
    val stack: ChildStack<RootConfig, RootChild> by navHost.stack.collectAsStateWithLifecycle()
    val actions = navHost.actions

    Children(
        modifier = modifier,
        stack = stack,
        animation = predictiveBackAnimation(
            backHandler = navHost.backHandler,
            onBack = actions::onPop,
            selector = { backEvent, _, _ -> androidPredictiveBackAnimatable(backEvent) },
        ),
    ) { child ->
        when (val childInstance = child.instance) {
            is RootChild.Example -> ExampleScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            is RootChild.Scratch -> ScratchScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
            is RootChild.Activation -> ActivationScreenUi(screen = childInstance.screen, modifier = Modifier.fillMaxSize())
        }
    }
}
