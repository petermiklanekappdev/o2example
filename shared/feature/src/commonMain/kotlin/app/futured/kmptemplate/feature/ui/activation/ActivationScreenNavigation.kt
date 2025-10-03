package app.futured.kmptemplate.feature.ui.activation

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.example.ExampleComponent
import app.futured.kmptemplate.feature.ui.scratch.ScratchComponent

internal interface ActivationScreenNavigation : NavigationActions {
    fun ActivationComponent.pop()
}
