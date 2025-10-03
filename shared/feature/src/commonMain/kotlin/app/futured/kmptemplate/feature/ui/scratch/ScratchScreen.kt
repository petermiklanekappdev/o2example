package app.futured.kmptemplate.feature.ui.scratch


import kotlinx.coroutines.flow.StateFlow

interface ScratchScreen {
    val viewState: StateFlow<ScratchViewState>
    val actions: Actions

    interface Actions {
        fun onReveal()
        fun onRevealed()
        fun onPop()
        fun onScratchChange(value: List<List<ScratchPoint>>)
    }
}
