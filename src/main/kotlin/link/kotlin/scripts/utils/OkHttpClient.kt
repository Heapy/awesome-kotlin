package link.kotlin.scripts.utils

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import java.util.concurrent.TimeUnit.SECONDS
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class OkHttpException(
    val request: Request,
    val exception: Exception
) : RuntimeException(exception)

suspend fun Call.await(): Response = suspendCoroutine { cont: Continuation<Response> ->
    this.enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            cont.resume(response)
        }

        override fun onFailure(call: Call, exception: IOException) {
            cont.resumeWithException(OkHttpException(call.request(), exception))
        }
    })
}

private val dispatcher = Dispatcher().apply {
    maxRequests = 200
    maxRequestsPerHost = 100
}

val okHttpClient: OkHttpClient = OkHttpClient
    .Builder()
    .readTimeout(30, SECONDS)
    .dispatcher(dispatcher)
    .build()
