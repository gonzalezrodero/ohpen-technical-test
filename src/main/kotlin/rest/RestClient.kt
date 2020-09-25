package rest

import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.ServiceUnavailableRetryStrategy
import org.apache.http.client.config.CookieSpecs
import org.apache.http.client.config.RequestConfig
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.impl.client.HttpClientBuilder
import org.apache.http.protocol.HttpContext
import org.slf4j.LoggerFactory
import java.io.IOException
import javax.ws.rs.core.Response

class RestClient {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val client: HttpClient

    init {
        val requestConfig = RequestConfig.custom()
            .setConnectTimeout(10000)
            .setConnectionRequestTimeout(10000)
            .setSocketTimeout(10000)
            .setCookieSpec(CookieSpecs.STANDARD)
            .build()

        client = HttpClientBuilder.create()
            .setDefaultRequestConfig(requestConfig)
            .setRetryHandler { _, executionCount, _ ->
                if (executionCount > RETRIES) {
                    log.error("Maximum retries reached")
                    false
                } else {
                    log.error("No response from server on $executionCount call")
                    true
                }
            }
            .setServiceUnavailableRetryStrategy(object : ServiceUnavailableRetryStrategy {
                override fun retryRequest(response: HttpResponse, executionCount: Int, context: HttpContext): Boolean {
                    if (executionCount > RETRIES) {
                        log.error("Maximum retries reached")
                        return false
                    }

                    val statusCode = response.statusLine.statusCode
                    if (Response.Status.Family.familyOf(statusCode) == Response.Status.Family.SERVER_ERROR) {
                        log.error("$statusCode response from server")
                        return true
                    }
                    return false
                }

                override fun getRetryInterval(): Long {
                    return 1000
                }
            })
            .build()
    }

    fun <T> execute(request: HttpUriRequest, responseHandler: (HttpResponse) -> T): T {
        var retries = RETRIES
        while (retries > 0) {
            try {
                return client.execute(request, responseHandler)
            } catch (e: IOException) {
                retries--
                log.error("Request retry. message: " + e.message + ". url: " + request.uri.toString() + ". method: " + request.method)
            }
        }
        log.error("Maximum retries reached")
        throw RuntimeException()
    }

    companion object {
        private const val RETRIES = 20
    }
}
