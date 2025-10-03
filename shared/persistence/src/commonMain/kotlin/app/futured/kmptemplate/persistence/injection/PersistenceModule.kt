package app.futured.kmptemplate.persistence.injection

import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import app.futured.kmptemplate.persistence.persistence.JsonPersistence
import app.futured.kmptemplate.persistence.persistence.PrimitivePersistence
import app.futured.kmptemplate.persistence.persistence.ScratchPersistence
import app.futured.kmptemplate.persistence.persistence.ScratchPersistenceImpl
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun persistenceModule() = module {
    // expect/actual Koin module
    includes(persistencePlatformModule())

    single {
        PreferenceDataStoreFactory.createWithPath(
            produceFile = { get(Qualifiers.DataStorePath) },
        )
    }

    singleOf(::PrimitivePersistence)
    single { JsonPersistence(get(), get(Qualifiers.PersistenceJson)) }

    single(Qualifiers.PersistenceJson) {
        Json {
            encodeDefaults = true
            isLenient = false
            ignoreUnknownKeys = true
            prettyPrint = false
        }
    }

    single<ScratchPersistence> { ScratchPersistenceImpl(get(), get()) }
}
