package app.futured.kmptemplate.feature.ui.activation


import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ActivationScreen {
    val viewState: StateFlow<ActivationViewState>
    val actions: Actions
    val events: Flow<ActivationEvent>

    interface Actions {
        fun onActivate()
        fun onPop()
    }
}
