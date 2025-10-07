package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.feature.api.ApiManager
import app.futured.kmptemplate.feature.domain.UiState
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveValidationCodeUseCase(
    private val apiManager: ApiManager
) : FlowUseCase<Unit, UiState<String?>>() {

    override fun build(args: Unit): Flow<UiState<String?>> {
        return apiManager.validationCodeState
    }
}