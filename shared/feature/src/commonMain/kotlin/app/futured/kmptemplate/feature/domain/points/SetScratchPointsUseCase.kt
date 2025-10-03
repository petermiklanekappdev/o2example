package app.futured.kmptemplate.feature.domain.points

import app.futured.arkitekt.crusecases.UseCase
import app.futured.kmptemplate.feature.ui.scratch.ScratchPoint
import app.futured.kmptemplate.persistence.data.ScratchPointDb
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import org.koin.core.annotation.Factory

@Factory
class SetScratchPointsUseCase(
    private val persistence: ScratchPersistence
) : UseCase<List<List<ScratchPoint>>, Unit>() {

    override suspend fun build(args: List<List<ScratchPoint>>) {
        persistence.setScratchPoints(args.map { it.map { ScratchPointDb(x = it.x, y = it.y) } })
    }
}