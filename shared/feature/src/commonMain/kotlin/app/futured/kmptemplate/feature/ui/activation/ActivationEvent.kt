package app.futured.kmptemplate.feature.ui.activation

import app.futured.arkitekt.decompose.presentation.UiEvent

sealed class ActivationEvent : UiEvent {
    data object ShowErrorDialog : ActivationEvent()
    data object ShowSuccessDialog : ActivationEvent()
}