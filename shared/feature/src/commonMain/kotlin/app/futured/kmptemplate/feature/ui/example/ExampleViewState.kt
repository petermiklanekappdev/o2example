package app.futured.kmptemplate.feature.ui.example

import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint

data class ExampleViewState(
    val points: List<List<ScratchPoint>>?,
    val code: String,
    val isRevealed: Boolean
) {
    companion object {
        val EMPTY = ExampleViewState(
            points = null,
            code = "",
            isRevealed = false
        )
    }
}
