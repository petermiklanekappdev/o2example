package app.futured.kmptemplate.feature.ui.scratch

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.code.GetScratchCodeUseCase
import app.futured.kmptemplate.feature.domain.points.GetScratchPointsUseCase
import app.futured.kmptemplate.feature.domain.points.SetScratchPointsUseCase
import app.futured.kmptemplate.feature.domain.reveal.GetIsScratchRevealedUseCase
import app.futured.kmptemplate.feature.domain.reveal.SetIsScratchRevealedUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import app.futured.kmptemplate.feature.ui.example.ExampleScreen
import app.futured.kmptemplate.feature.ui.example.ExampleScreenNavigation
import app.futured.kmptemplate.feature.ui.example.ExampleViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@GenerateFactory
@Factory
internal class ScratchComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ScratchScreenNavigation,
    private val getScratchCodeUseCase: GetScratchCodeUseCase,
    private val getScratchPointsUseCase: GetScratchPointsUseCase,
    private val getIsScratchRevealedUseCase: GetIsScratchRevealedUseCase,
    private val setIsScratchRevealedUseCase: SetIsScratchRevealedUseCase,
    private val setScratchPointsUseCase: SetScratchPointsUseCase
) : ScreenComponent<ScratchViewState, Nothing, ScratchScreenNavigation>(
    componentContext = componentContext,
    defaultState = ScratchViewState.EMPTY,
),
    ScratchScreen,
    ScratchScreenNavigation by navigation,
    ScratchScreen.Actions {

    override val actions: ScratchScreen.Actions = this
    override val viewState: StateFlow<ScratchViewState> = componentState.asStateFlow()

    init {
        getScratchCodeUseCase.execute {
            onSuccess {
                componentState.update { oldState -> oldState.copy(code = it ?: "") }
            }
        }
        getScratchPointsUseCase.execute {
            onSuccess {
                componentState.update { oldState -> oldState.copy(points = it) }
            }
        }
        getIsScratchRevealedUseCase.execute {
            onSuccess {
                componentState.update { oldState -> oldState.copy(isRevealed = it) }
            }
        }
    }

    override fun onPop() {
        pop()
    }

    override fun onReveal() {
        if (componentState.value.isRevealed) return
        viewModelScope.launch {
            componentState.update { oldState ->
                oldState.copy(isLoading = true)
            }
            delay(2000)
            onRevealed()
            componentState.update { oldState ->
                oldState.copy(isLoading = false)
            }
        }
    }

    override fun onRevealed() {
        setIsScratchRevealedUseCase.execute(true) {
            onSuccess {
                componentState.update { oldState ->
                    oldState.copy(isRevealed = true)
                }
            }
        }
    }

    override fun onScratchChange(value: List<List<ScratchPoint>>) {
        componentState.update { oldState ->
            oldState.copy(points = value)
        }
        setScratchPointsUseCase.execute(value) {
            onSuccess {
                componentState.update { oldState ->
                    oldState.copy(points = value)
                }
            }
        }
    }
}
