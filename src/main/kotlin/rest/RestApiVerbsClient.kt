package rest

import com.google.gson.JsonObject
import org.apache.http.HttpHeaders
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPatch
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpPut
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.ContentType
import org.apache.http.entity.StringEntity
import org.slf4j.MDC
import java.net.URI

class RestApiVerbsClient(private val restClient: RestClient, private val authToken: String) {

    fun <T> doGet(
        uri: URI,
        responseHandler: (HttpResponse) -> T,
        contentType: ContentType = ContentType.APPLICATION_JSON,
        accept: ContentType = ContentType.APPLICATION_JSON
    ): T =
        HttpGet(uri).setHeaders(contentType, accept, authToken).run { restClient.execute(this, responseHandler) }

    fun <T> doPost(
        uri: URI,
        responseHandler: (HttpResponse) -> T,
        contentType: ContentType = ContentType.APPLICATION_JSON,
        accept: ContentType = ContentType.APPLICATION_JSON
    ): T {
        val httpPost = HttpPost(uri)
//        httpPost.entity = StringEntity(body.toString())

        return httpPost.setHeaders(contentType, accept, authToken).run { restClient.execute(this, responseHandler) }
    }

    fun <T> doPut(
        uri: URI,
        responseHandler: (HttpResponse) -> T,
        body: JsonObject,
        contentType: ContentType = ContentType.APPLICATION_JSON,
        accept: ContentType = ContentType.APPLICATION_JSON
    ): T {

        val httpPut = HttpPut(uri)
        httpPut.entity = StringEntity(body.toString())

        return httpPut.setHeaders(contentType, accept, authToken).run { restClient.execute(this, responseHandler) }
    }

    fun <T> doPatch(
        uri: URI,
        responseHandler: (HttpResponse) -> T,
        body: JsonObject,
        contentType: ContentType = ContentType.APPLICATION_JSON,
        accept: ContentType = ContentType.APPLICATION_JSON
    ): T {

        val httpPatch = HttpPatch(uri)
        httpPatch.entity = StringEntity(body.toString())

        return httpPatch.setHeaders(contentType, accept, authToken).run { restClient.execute(this, responseHandler) }
    }

    private fun HttpUriRequest.setHeaders(contentType: ContentType, accept: ContentType, token: String): HttpUriRequest {
        this.addHeader(HttpHeaders.AUTHORIZATION, token)
        this.addHeader(X_CORRELATION_ID, MDC.get("correlationId"))
        this.addHeader(HttpHeaders.CONTENT_TYPE, contentType.toString())
        this.addHeader(HttpHeaders.ACCEPT, accept.toString())

        return this
    }

    companion object {
        const val X_CORRELATION_ID = "X-Correlation-Id"
    }
}
