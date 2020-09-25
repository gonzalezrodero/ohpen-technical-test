package rest

import org.apache.http.HttpHeaders
import org.apache.http.HttpResponse
import org.apache.http.client.methods.HttpGet
import org.apache.http.client.methods.HttpPost
import org.apache.http.client.methods.HttpUriRequest
import org.apache.http.entity.ContentType
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

        return httpPost.setHeaders(contentType, accept, authToken).run { restClient.execute(this, responseHandler) }
    }

    private fun HttpUriRequest.setHeaders(
        contentType: ContentType,
        accept: ContentType,
        token: String
    ): HttpUriRequest {
        this.addHeader(HttpHeaders.AUTHORIZATION, token)
        this.addHeader(HttpHeaders.CONTENT_TYPE, contentType.toString())
        this.addHeader(HttpHeaders.ACCEPT, accept.toString())

        return this
    }
}
