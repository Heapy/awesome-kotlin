package link.kotlin.scripts.utils

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.cancelFutureOnCancellation
import kotlinx.coroutines.suspendCancellableCoroutine
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.concurrent.FutureCallback
import org.apache.http.impl.cookie.IgnoreSpecProvider
import org.apache.http.impl.nio.client.HttpAsyncClients
import org.apache.http.nio.client.HttpAsyncClient
import kotlin.concurrent.thread

interface HttpClient {
    suspend fun execute(request: HttpUriRequest): HttpResponse

    companion object
}

fun HttpResponse.body(): String {
    return entity.content.reader().readText()
}

private class DefaultHttpClient(
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

        cont.cancelFutureOnCancellation(future)
        Unit
    }
}

fun HttpClient.Companion.default(): HttpClient {
    val ua = "Mozilla/5.0 (X11; Fedora; Linux x86_64; rv:74.0) Gecko/20100101 Firefox/74.0"

    val asyncClient = HttpAsyncClients.custom()
        .setUserAgent(ua)
        .setMaxConnPerRoute(10)
        .setMaxConnTotal(100)
        .setDefaultCookieSpecRegistry { IgnoreSpecProvider() }
        .build()

    Runtime.getRuntime().addShutdownHook(thread(start = false) {
        logger<HttpClient>().info("HttpClient Shutdown called...")
        asyncClient.close()
        logger<HttpClient>().info("HttpClient Shutdown done...")
    })

    asyncClient.start()

    return DefaultHttpClient(
        client = asyncClient
    )
}
