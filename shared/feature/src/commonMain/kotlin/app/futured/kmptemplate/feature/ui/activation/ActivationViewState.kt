package app.futured.kmptemplate.feature.ui.activation

data class ActivationViewState(
    val code: String,
    val isLoading: Boolean
) {
    companion object {
        val EMPTY = ActivationViewState(
            code = "",
            isLoading = false
        )
    }
}
