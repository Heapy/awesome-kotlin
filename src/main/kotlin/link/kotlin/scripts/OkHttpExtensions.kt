package link.kotlin.scripts

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.CompletableFuture


class OkHttpException(val request: Request,
                      val exception: Exception) : RuntimeException(exception)

fun Call.async(): CompletableFuture<Response> {
    val future = CompletableFuture<Response>()

    this.enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            future.complete(response)
        }

        override fun onFailure(call: Call, exception: IOException) {
            future.completeExceptionally(OkHttpException(call.request(), exception))
        }
    })

    return future
}

fun <T> okhttp(call: (OkHttpClient) -> T): T {
    val client = OkHttpClient()

    client.dispatcher().maxRequests = 200
    client.dispatcher().maxRequestsPerHost = 100

    try {
        return call(client)
    } finally {
        client.dispatcher().cancelAll()
        client.dispatcher().executorService().shutdown()
    }
}