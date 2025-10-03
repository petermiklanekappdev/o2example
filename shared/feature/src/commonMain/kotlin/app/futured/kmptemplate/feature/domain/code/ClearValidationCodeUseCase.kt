package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.api.ApiManager
import org.koin.core.annotation.Factory

@Factory
class ClearValidationCodeUseCase(
    private val apiManager: ApiManager
) : UseCase<Unit, Unit>() {
    override suspend fun build(args: Unit) {
        apiManager.clearValidationCode()
    }
}