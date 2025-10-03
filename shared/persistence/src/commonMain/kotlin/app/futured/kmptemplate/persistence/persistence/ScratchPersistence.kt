package app.futured.kmptemplate.persistence.persistence

import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import app.futured.kmptemplate.persistence.data.ScratchPointDb
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import org.koin.core.annotation.Single

interface ScratchPersistence {
    suspend fun setIsScratchRevealed(value: Boolean)
    suspend fun getIsScratchRevealed(): Boolean
    fun getIsScratchRevealedFlow(): Flow<Boolean>
    suspend fun setScratchCode(value: String?)
    suspend fun getScratchCode(): String?
    fun getScratchCodeFlow(): Flow<String>
    suspend fun getScratchPoints(): List<List<ScratchPointDb>>
    fun getScratchPointsFlow(): Flow<List<List<ScratchPointDb>>>
    suspend fun setScratchPoints(value: List<List<ScratchPointDb>>)
}

@Single
internal class ScratchPersistenceImpl(
    private val jsonPersistence: JsonPersistence,
    private val primitivePersistence: PrimitivePersistence
) : ScratchPersistence {

    private companion object {
        val SCRATCH_POINTS_KEY = stringPreferencesKey("SCRATCH_POINTS_KEY")
        val SCRATCH_CODE_KEY = stringPreferencesKey("SCRATCH_CODE_KEY")
        val SCRATCH_IS_REVEALED_KEY = booleanPreferencesKey("SCRATCH_IS_REVEALED_KEY")
    }

    override suspend fun setIsScratchRevealed(value: Boolean) {
        primitivePersistence.save(SCRATCH_IS_REVEALED_KEY, value)
    }

    override suspend fun getIsScratchRevealed(): Boolean {
        return primitivePersistence.get(SCRATCH_IS_REVEALED_KEY) ?: false
    }

    override fun getIsScratchRevealedFlow(): Flow<Boolean> {
        return primitivePersistence.observe(SCRATCH_IS_REVEALED_KEY)
    }

    override suspend fun setScratchCode(value: String?) {
        if (value == null) {
            primitivePersistence.delete(SCRATCH_CODE_KEY)
        } else {
            primitivePersistence.save(SCRATCH_CODE_KEY, value)
        }
    }

    override suspend fun getScratchCode(): String? {
        return primitivePersistence.get(SCRATCH_CODE_KEY)
    }

    override fun getScratchCodeFlow(): Flow<String> {
        return primitivePersistence.observe(SCRATCH_CODE_KEY)
    }

    override suspend fun getScratchPoints(): List<List<ScratchPointDb>> {
        return jsonPersistence.get<List<List<ScratchPointDb>>>(SCRATCH_POINTS_KEY) ?: emptyList()
    }

    override fun getScratchPointsFlow(): Flow<List<List<ScratchPointDb>>> {
        return jsonPersistence.observe<List<List<ScratchPointDb>>>(SCRATCH_POINTS_KEY)
            .filterNotNull()
    }

    override suspend fun setScratchPoints(value: List<List<ScratchPointDb>>) {
        return jsonPersistence.save(SCRATCH_POINTS_KEY, value)
    }
}