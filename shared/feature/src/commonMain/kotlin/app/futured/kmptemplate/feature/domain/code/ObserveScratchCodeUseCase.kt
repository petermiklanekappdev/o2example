package app.futured.kmptemplate.feature.domain.code

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import app.futured.kmptemplate.persistence.data.ScratchPointDb
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveScratchCodeUseCase(
    private val persistence: ScratchPersistence
) : FlowUseCase<Unit, String>() {

    override fun build(args: Unit): Flow<String> {
        return persistence.getScratchCodeFlow()
    }
}