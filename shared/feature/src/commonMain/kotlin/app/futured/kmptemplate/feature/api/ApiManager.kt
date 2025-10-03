package app.futured.kmptemplate.feature.api

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
    val validationCodeState = MutableStateFlow<String?>(null)
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun activateCode(code: String) {
        scope.launch {
            validationCodeState.value = exampleApi.getVersion(code).getOrThrow().android
        }
    }

    fun clearValidationCode() {
        validationCodeState.value = null
    }
}