package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class SetScratchCodeUseCase(
    private val persistence: ScratchPersistence
) : UseCase<String?, Unit>() {

    override suspend fun build(args: String?) {
        return persistence.setScratchCode(args)
    }
}