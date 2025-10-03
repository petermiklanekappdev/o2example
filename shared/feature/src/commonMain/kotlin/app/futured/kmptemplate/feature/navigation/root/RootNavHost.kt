package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.ui.activation.ActivationScreen
import app.futured.kmptemplate.feature.ui.example.ExampleScreen
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreen
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.essenty.backhandler.BackHandler
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface RootNavHost {
    val backHandler: BackHandler
    val stack: StateFlow<ChildStack<RootConfig, RootChild>>
    val actions: Actions

    interface Actions {
        fun onDeepLink(uri: String)
        fun onPop()
    }
}

@Serializable
sealed interface RootConfig {

    @Serializable
    data object Example : RootConfig

    @Serializable
    data object Scratch : RootConfig

    @Serializable
    data object Activation : RootConfig
}

sealed interface RootChild {
    data class Example(val screen: ExampleScreen) : RootChild

    data class Scratch(val screen: ScratchScreen) : RootChild

    data class Activation(val screen: ActivationScreen) : RootChild
}
