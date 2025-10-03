package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.api.ApiManager
import app.futured.kmptemplate.network.rest.api.ExampleApi
import app.futured.kmptemplate.network.rest.result.getOrThrow
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class ActivateScratchCodeUseCase(
    private val persistence: ScratchPersistence,
    private val apiManager: ApiManager
) : UseCase<Unit, Unit>() {

    override suspend fun build(args: Unit) {
        val code = persistence.getScratchCode() ?: return
        return apiManager.activateCode(code)
    }
}