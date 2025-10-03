package app.futured.kmptemplate.feature.navigation.root

import app.futured.kmptemplate.feature.ui.activation.ActivationComponent
import app.futured.kmptemplate.feature.ui.activation.ActivationScreenNavigation
import app.futured.kmptemplate.feature.ui.example.ExampleComponent
import app.futured.kmptemplate.feature.ui.example.ExampleScreenNavigation
import app.futured.kmptemplate.feature.ui.scratch.ScratchComponent
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreenNavigation
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew

internal interface RootNavHostNavigation :
    ExampleScreenNavigation,
    ActivationScreenNavigation,
    ScratchScreenNavigation {
    val stackNavigator: StackNavigation<RootConfig>
}

internal class RootNavHostNavigator : RootNavHostNavigation {
    override val stackNavigator: StackNavigation<RootConfig> = StackNavigation()

    override fun ExampleComponent.navigateOnScratch() {
        stackNavigator.pushNew(RootConfig.Scratch)
    }

    override fun ExampleComponent.navigateOnActivation() {
        stackNavigator.pushNew(RootConfig.Activation)
    }

    override fun ActivationComponent.pop() {
        stackNavigator.pop()
    }

    override fun ScratchComponent.pop() {
        stackNavigator.pop()
    }
}
