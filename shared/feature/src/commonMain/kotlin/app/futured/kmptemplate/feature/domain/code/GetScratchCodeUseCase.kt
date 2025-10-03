package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class GetScratchCodeUseCase(
    private val persistence: ScratchPersistence
) : UseCase<Unit, String?>() {

    override suspend fun build(args: Unit): String? {
        return persistence.getScratchCode()
    }
}