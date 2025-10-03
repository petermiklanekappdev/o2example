package app.futured.kmptemplate.feature.ui.example


import kotlinx.coroutines.flow.StateFlow

interface ExampleScreen {
    val viewState: StateFlow<ExampleViewState>
    val actions: Actions

    interface Actions {
        fun onScratch()
        fun onActivation()
        fun onResetData()
    }
}
