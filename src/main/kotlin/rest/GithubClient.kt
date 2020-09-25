package rest

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import org.apache.http.Consts
import org.apache.http.HttpResponse
import org.apache.http.entity.ContentType
import org.apache.http.util.EntityUtils
import rest.jsonSchemas.GithubUser
import rest.jsonSchemas.InteractionLimits
import rest.jsonSchemas.RegistrationToken
import javax.ws.rs.core.UriBuilder

class GithubClient(private val restApiVerbsClient: RestApiVerbsClient) {

    fun getUserInfo(username: String): GithubUser {
        val requestUri =
            UriBuilder.fromUri(GITHUB_URL + USER).build(username)

        return restApiVerbsClient.doGet(
            requestUri,
            getUserInfoHandler,
            ContentType.APPLICATION_JSON,
            ContentType.create("application/vnd.github.sombra-preview+json", Consts.UTF_8)
        )
    }

    fun createRegistrationTokenForARepo(owner: String, repo: String): RegistrationToken {
        val requestUri =
            UriBuilder.fromUri(GITHUB_URL + REGISTRATION_TOKEN).build(owner, repo)

        return restApiVerbsClient.doPost(
            requestUri,
            createRegistrationTokenHandler,
            ContentType.APPLICATION_JSON,
            ContentType.create("application/vnd.github.sombra-preview+json", Consts.UTF_8)
        )
    }

    fun requestInteractionLimitsForARepo(owner: String, repo: String): InteractionLimits {
        val requestUri =
            UriBuilder.fromUri(GITHUB_URL + INTERACTION_LIMITS).build(owner, repo)

        return restApiVerbsClient.doGet(
            requestUri,
            getInteractionLimitsHandler,
            ContentType.APPLICATION_JSON,
            ContentType.create("application/vnd.github.sombra-preview+json", Consts.UTF_8)
        )
    }

    companion object {
        private const val GITHUB_URL = "https://api.github.com"
        private const val USER = "/users/{username}"
        private const val REGISTRATION_TOKEN = "/repos/{owner}/{repo}/actions/runners/registration-token"
        private const val INTERACTION_LIMITS = "/repos/{owner}/{repo}/interaction-limits"

        private val getUserInfoHandler: (HttpResponse) -> GithubUser = { response: HttpResponse ->
            when (val statusCode = response.statusLine.statusCode) {
                200 -> Gson().fromJson(EntityUtils.toString(response.entity).asJsonObject(), GithubUser::class.java)
                else -> throw RuntimeException("Unexpected response status: $statusCode")
            }
        }

        private val createRegistrationTokenHandler: (HttpResponse) -> RegistrationToken = { response: HttpResponse ->
            when (val statusCode = response.statusLine.statusCode) {
                201 -> Gson().fromJson(
                    EntityUtils.toString(response.entity).asJsonObject(),
                    RegistrationToken::class.java
                )
                else -> throw RuntimeException("Unexpected response status: $statusCode")
            }
        }

        private val getInteractionLimitsHandler: (HttpResponse) -> InteractionLimits = { response: HttpResponse ->
            when (val statusCode = response.statusLine.statusCode) {
                200 -> Gson().fromJson(
                    EntityUtils.toString(response.entity).asJsonObject(),
                    InteractionLimits::class.java
                )
                else -> throw RuntimeException("Unexpected response status: $statusCode")
            }
        }

        private fun String.asJsonObject(): JsonObject = JsonParser().parse(this).asJsonObject
    }
}