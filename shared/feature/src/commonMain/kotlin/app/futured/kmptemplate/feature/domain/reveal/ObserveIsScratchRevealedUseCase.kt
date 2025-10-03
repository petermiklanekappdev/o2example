package app.futured.kmptemplate.feature.domain.reveal

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import kotlinx.coroutines.flow.Flow
import org.koin.core.annotation.Factory

@Factory
class ObserveIsScratchRevealedUseCase(
    private val persistence: ScratchPersistence
) : FlowUseCase<Unit, Boolean>() {

    override fun build(args: Unit): Flow<Boolean> {
        return persistence.getIsScratchRevealedFlow()
    }
}