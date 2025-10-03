package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.feature.api.ApiManager
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveValidationCodeUseCase(
    private val apiManager: ApiManager
) : FlowUseCase<Unit, String?>() {

    override fun build(args: Unit): Flow<String?> {
        return apiManager.validationCodeState
    }
}