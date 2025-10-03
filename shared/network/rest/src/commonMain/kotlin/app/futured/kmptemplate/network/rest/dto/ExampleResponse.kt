package app.futured.kmptemplate.network.rest.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExampleResponse(
    @SerialName("android") val android: String? = null
)
