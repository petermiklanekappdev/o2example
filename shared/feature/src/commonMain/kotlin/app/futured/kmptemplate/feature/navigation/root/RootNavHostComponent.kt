package app.futured.kmptemplate.feature.navigation.root

import app.futured.arkitekt.decompose.ext.asStateFlow
import app.futured.kmptemplate.feature.ui.activation.ActivationComponentFactory
import app.futured.kmptemplate.feature.ui.base.AppComponent
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.example.ExampleComponent
import app.futured.kmptemplate.feature.ui.example.ExampleComponentFactory
import app.futured.kmptemplate.feature.ui.example.ExampleScreenNavigation
import app.futured.kmptemplate.feature.ui.scratch.ScratchComponent
import app.futured.kmptemplate.feature.ui.scratch.ScratchComponentFactory
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreenNavigation
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.essenty.lifecycle.doOnCreate
import kotlinx.coroutines.flow.StateFlow
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@Factory
internal class RootNavHostComponent(
    @InjectedParam componentContext: AppComponentContext,
) : AppComponent<RootNavHostViewState, Nothing>(componentContext, RootNavHostViewState), RootNavHost {

    private val rootNavigator: RootNavHostNavigation = RootNavHostNavigator()

    override val stack: StateFlow<ChildStack<RootConfig, RootChild>> = childStack(
        source = rootNavigator.stackNavigator,
        serializer = RootConfig.serializer(),
        initialStack = { listOf(RootConfig.Example) },
        handleBackButton = false,
        childFactory = { config, childContext ->
            when (config) {
                RootConfig.Example -> {
                    RootChild.Example(
                        ExampleComponentFactory.createComponent(
                            componentContext = childContext,
                            navigation = rootNavigator
                        )
                    )
                }
                RootConfig.Scratch -> {
                    RootChild.Scratch(
                        ScratchComponentFactory.createComponent(
                            componentContext = childContext,
                            navigation = rootNavigator
                        )
                    )
                }
                RootConfig.Activation -> {
                    RootChild.Activation(
                        ActivationComponentFactory.createComponent(
                            componentContext = childContext,
                            navigation = rootNavigator
                        )
                    )
                }
            }
        },
    ).asStateFlow()

    override val actions: RootNavHost.Actions = object : RootNavHost.Actions {
        override fun onDeepLink(uri: String) {
            // Nothing to do here
        }

        override fun onPop() {
            rootNavigator.stackNavigator.pop()
        }
    }
}
