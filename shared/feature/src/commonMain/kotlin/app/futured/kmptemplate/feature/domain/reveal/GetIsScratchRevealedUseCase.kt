package app.futured.kmptemplate.feature.domain.reveal

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class GetIsScratchRevealedUseCase(
    private val persistence: ScratchPersistence
) : UseCase<Unit, Boolean>() {

    override suspend fun build(args: Unit): Boolean {
        return persistence.getIsScratchRevealed()
    }
}