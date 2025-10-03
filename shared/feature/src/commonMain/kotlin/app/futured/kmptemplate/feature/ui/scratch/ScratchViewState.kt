package app.futured.kmptemplate.feature.ui.scratch

import kotlinx.serialization.Serializable

data class ScratchViewState(
    val code: String,
    val isRevealed: Boolean,
    val isLoading: Boolean,
    val points: List<List<ScratchPoint>>?
) {
    companion object {
        val EMPTY = ScratchViewState(
            code = "",
            isRevealed = false,
            isLoading = false,
            points = null
        )
    }
}

@Serializable
data class ScratchPoint(val x: Double, val y: Double)