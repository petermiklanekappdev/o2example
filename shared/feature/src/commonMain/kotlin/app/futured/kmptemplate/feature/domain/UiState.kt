package app.futured.kmptemplate.feature.domain

sealed class UiState<out T>

data object StateLoading : UiState<Nothing>()

data class StateContent<T>(val item: T) : UiState<T>()

data class StateError(val throwable: Throwable) : UiState<Nothing>()

data object StateEmpty : UiState<Nothing>()