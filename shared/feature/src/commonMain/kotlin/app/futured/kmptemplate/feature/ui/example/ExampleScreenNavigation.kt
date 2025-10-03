package app.futured.kmptemplate.feature.ui.example

import app.futured.arkitekt.decompose.navigation.NavigationActions

internal interface ExampleScreenNavigation : NavigationActions {
    fun ExampleComponent.navigateOnScratch()
    fun ExampleComponent.navigateOnActivation()
}
