package app.futured.kmptemplate.network.rest.api

import app.futured.kmptemplate.network.rest.dto.ExampleResponse
import app.futured.kmptemplate.network.rest.result.NetworkResult
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface ExampleApi {

    @GET("version")
    suspend fun getVersion(
        @Query("code") code: String,
    ): NetworkResult<ExampleResponse>
}
