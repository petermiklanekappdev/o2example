package app.futured.kmptemplate.feature.ui.example

import app.futured.factorygenerator.annotation.GenerateFactory
import app.futured.kmptemplate.feature.domain.code.GetScratchCodeUseCase
import app.futured.kmptemplate.feature.domain.code.ObserveScratchCodeUseCase
import app.futured.kmptemplate.feature.domain.code.SetScratchCodeUseCase
import app.futured.kmptemplate.feature.domain.points.GetScratchPointsUseCase
import app.futured.kmptemplate.feature.domain.points.ObserveScratchPointsUseCase
import app.futured.kmptemplate.feature.domain.points.SetScratchPointsUseCase
import app.futured.kmptemplate.feature.domain.reveal.ObserveIsScratchRevealedUseCase
import app.futured.kmptemplate.feature.domain.reveal.SetIsScratchRevealedUseCase
import app.futured.kmptemplate.feature.ui.base.AppComponentContext
import app.futured.kmptemplate.feature.ui.base.ScreenComponent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.annotation.Factory
import org.koin.core.annotation.InjectedParam
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
@GenerateFactory
@Factory
internal class ExampleComponent(
    @InjectedParam componentContext: AppComponentContext,
    @InjectedParam override val navigation: ExampleScreenNavigation,
    private val observeScratchCodeUseCase: ObserveScratchCodeUseCase,
    private val observeScratchPointsUseCase: ObserveScratchPointsUseCase,
    private val observeIsScratchRevealedUseCase: ObserveIsScratchRevealedUseCase,
    private val getScratchCodeUseCase: GetScratchCodeUseCase,
    private val setScratchCodeUseCase: SetScratchCodeUseCase,
    private val setScratchPointsUseCase: SetScratchPointsUseCase,
    private val setIsScratchRevealedUseCase: SetIsScratchRevealedUseCase,
) : ScreenComponent<ExampleViewState, Nothing, ExampleScreenNavigation>(
    componentContext = componentContext,
    defaultState = ExampleViewState.EMPTY,
),
    ExampleScreen,
    ExampleScreenNavigation by navigation,
    ExampleScreen.Actions {

    override val actions: ExampleScreen.Actions = this
    override val viewState: StateFlow<ExampleViewState> = componentState.asStateFlow()

    init {
        getScratchCodeUseCase.execute {
            onSuccess { code ->
                if (code == null) {
                    val newCode = Uuid.random().toString().take(20)
                    setScratchCodeUseCase.execute(newCode) {}
                }
            }
        }
        observeScratchCodeUseCase.execute {
            onNext {
                componentState.update { oldState -> oldState.copy(code = it) }
            }
        }
        observeScratchPointsUseCase.execute {
            onNext {
                componentState.update { oldState -> oldState.copy(points = it) }
            }
        }
        observeIsScratchRevealedUseCase.execute {
            onNext {
                componentState.update { oldState -> oldState.copy(isRevealed = it) }
            }
        }
    }

    override fun onScratch() {
        navigateOnScratch()
    }

    override fun onActivation() {
        navigateOnActivation()
    }

    override fun onResetData() {
        val newCode = Uuid.random().toString().take(20)
        setScratchCodeUseCase.execute(newCode) {}
        setScratchPointsUseCase.execute(emptyList()) {}
        setIsScratchRevealedUseCase.execute(false) {}
    }
}
