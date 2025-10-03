package app.futured.kmptemplate.feature.ui.scratch

import app.futured.arkitekt.decompose.navigation.NavigationActions
import app.futured.kmptemplate.feature.ui.example.ExampleComponent

internal interface ScratchScreenNavigation : NavigationActions {
    fun ScratchComponent.pop()
}
