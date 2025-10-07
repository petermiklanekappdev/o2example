package app.futured.kmptemplate.feature.ui.activation

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.StateContent
import app.futured.kmptemplate.feature.domain.StateError
import app.futured.kmptemplate.feature.domain.StateLoading
import app.futured.kmptemplate.feature.domain.code.ActivateScratchCodeUseCase
import app.futured.kmptemplate.feature.domain.code.ClearValidationCodeUseCase
import app.futured.kmptemplate.feature.domain.code.ObserveValidationCodeUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.example.ExampleScreen
import app.futured.kmptemplate.feature.ui.example.ExampleScreenNavigation
import app.futured.kmptemplate.feature.ui.example.ExampleViewState
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreen
import app.futured.kmptemplate.feature.ui.scratch.ScratchScreenNavigation
import app.futured.kmptemplate.feature.ui.scratch.ScratchViewState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam

@GenerateFactory
@Factory
internal class ActivationComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ActivationScreenNavigation,
    private val activateScratchCodeUseCase: ActivateScratchCodeUseCase,
    private val observeValidationCodeUseCase: ObserveValidationCodeUseCase,
    private val clearValidationCodeUseCase: ClearValidationCodeUseCase
) : ScreenComponent<ActivationViewState, ActivationEvent, ActivationScreenNavigation>(
    componentContext = componentContext,
    defaultState = ActivationViewState.Companion.EMPTY,
),
    ActivationScreen,
    ActivationScreenNavigation by navigation,
    ActivationScreen.Actions {

    override val actions: ActivationScreen.Actions = this
    override val viewState: StateFlow<ActivationViewState> = componentState.asStateFlow()

    init {
        observeValidationCodeUseCase.execute {
            onNext { state ->
                when {
                    state is StateLoading -> {
                        componentState.update { oldState -> oldState.copy(isLoading = true) }
                    }

                    state is StateError -> {
                        sendUiEvent(ActivationEvent.ShowErrorDialog)
                        componentState.update { oldState -> oldState.copy(isLoading = false) }
                    }

                    state is StateContent -> {
                        val validation = state.item?.toLongOrNull() ?: 0

                        if (validation > VALIDATION_MIN_VALUE) {
                            sendUiEvent(ActivationEvent.ShowSuccessDialog)
                        } else {
                            sendUiEvent(ActivationEvent.ShowErrorDialog)
                        }

                        componentState.update { oldState -> oldState.copy(isLoading = false) }
                    }
                }
            }
            onError {
                sendUiEvent(ActivationEvent.ShowErrorDialog)
                componentState.update { oldState -> oldState.copy(isLoading = false) }
            }
        }
    }

    override fun onActivate() {
        activateScratchCodeUseCase.execute {
            onStart {
                componentState.update { oldState -> oldState.copy(isLoading = true) }
            }
            onSuccess { /*Nothing to do here*/ }
            onError {
                sendUiEvent(ActivationEvent.ShowErrorDialog)
                componentState.update { oldState -> oldState.copy(isLoading = false) }
            }
        }
    }

    override fun onPop() {
        pop()
    }

    override fun onDismissDialog() {
        clearValidationCodeUseCase.execute { }
    }

    companion object {
        const val VALIDATION_MIN_VALUE = 277028L
    }
}
