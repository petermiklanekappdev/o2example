package app.futured.kmptemplate.feature.domain.points

import app.futured.arkitekt.crusecases.FlowUseCase
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import app.futured.kmptemplate.persistence.data.ScratchPointDb
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.koin.core.annotation.Factory

@Factory
class ObserveScratchPointsUseCase(
    private val persistence: ScratchPersistence
) : FlowUseCase<Unit, List<List<ScratchPoint>>>() {

    override fun build(args: Unit): Flow<List<List<ScratchPoint>>> {
        return persistence.getScratchPointsFlow()
            .map { it.map { it.map { ScratchPoint(x = it.x, y = it.y) } } }
    }
}