package app.futured.kmptemplate.feature.api

import app.futured.kmptemplate.feature.domain.StateContent
import app.futured.kmptemplate.feature.domain.StateEmpty
import app.futured.kmptemplate.feature.domain.StateError
import app.futured.kmptemplate.feature.domain.StateLoading
import app.futured.kmptemplate.feature.domain.UiState
import app.futured.kmptemplate.network.rest.api.ExampleApi
import app.futured.kmptemplate.network.rest.result.getOrThrow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.annotation.Single

@Single
class ApiManager(
    private val exampleApi: ExampleApi
) {
    val validationCodeState = MutableStateFlow<UiState<String?>>(StateEmpty)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun activateCode(code: String) {
        scope.launch {
            runCatching {
                validationCodeState.value = StateLoading
                exampleApi.getVersion(code).getOrThrow().android
            }.onSuccess { androidVersion ->
                validationCodeState.value = StateContent(androidVersion)
            }.onFailure { error ->
                validationCodeState.value = StateError(error)
            }
        }
    }

    fun clearValidationCode() {
        validationCodeState.value = StateEmpty
    }
}