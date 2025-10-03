package app.futured.kmptemplate.feature.domain.points

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class GetScratchPointsUseCase(
    private val persistence: ScratchPersistence
) : UseCase<Unit, List<List<ScratchPoint>>>() {

    override suspend fun build(args: Unit): List<List<ScratchPoint>> {
        return persistence.getScratchPoints().map { it.map { ScratchPoint(x = it.x, y = it.y) } }
    }
}