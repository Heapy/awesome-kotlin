package link.kotlin.scripts.utils

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.concurrent.FutureCallback
import org.apache.http.nio.client.HttpAsyncClient

interface HttpClient {
    suspend fun execute(request: HttpUriRequest): HttpResponse
}

fun HttpResponse.body(): String {
    return entity.content.reader().readText()
}

internal class DefaultHttpClient(
    private val client: HttpAsyncClient
) : HttpClient {
    override suspend fun execute(request: HttpUriRequest): HttpResponse {
        return client.execute(request)
    }
}

private suspend fun HttpAsyncClient.execute(request: HttpUriRequest): HttpResponse {
    return suspendCancellableCoroutine { cont: CancellableContinuation<HttpResponse> ->
        val future = this.execute(request, object : FutureCallback<HttpResponse> {
            override fun completed(result: HttpResponse) {
                cont.resumeWith(Result.success(result))
            }

            override fun cancelled() {
                if (cont.isCancelled) return
                cont.resumeWith(Result.failure(CancellationException("Cancelled")))
            }

            override fun failed(ex: Exception) {
                cont.resumeWith(Result.failure(ex))
            }
        })

        cont.invokeOnCancellation { future.cancel(false) }
        Unit
    }
}
