package app.futured.kmptemplate.feature.domain.reveal

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class SetIsScratchRevealedUseCase(
    private val persistence: ScratchPersistence
) : UseCase<Boolean, Unit>() {

    override suspend fun build(args: Boolean) {
        persistence.setIsScratchRevealed(args)
    }
}