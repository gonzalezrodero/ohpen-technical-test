package stepDefinitions

import io.cucumber.java.en.Then
import io.cucumber.java.en.When
import org.assertj.core.api.Assertions.assertThat
import org.slf4j.LoggerFactory
import rest.GithubClient
import rest.RestApiVerbsClient
import rest.RestClient
import rest.jsonSchemas.GithubUser
import rest.jsonSchemas.InteractionLimits
import rest.jsonSchemas.RegistrationToken

class OhpenPart1 {

    private val log = LoggerFactory.getLogger(this::class.java)
    private val restClient = RestClient()
    private val restApiVerbsClient = RestApiVerbsClient(restClient, System.getenv("GITHUB_TOKEN"))
    private val githubClient = GithubClient(restApiVerbsClient)

    private lateinit var githubUser: GithubUser
    private lateinit var registrationToken: RegistrationToken
    private lateinit var interactionLimits: InteractionLimits

    @When("requesting information for the user {string}")
    fun requestingInformationForTheUser(user: String) {
        githubUser = githubClient.getUserInfo(user)
    }

    @When("creating a registration token for a the repo {string} of {string}")
    fun creatingARegistrationTokenForTheRepoOf(repo: String, owner: String) {
        registrationToken = githubClient.createRegistrationTokenForARepo(owner, repo)
    }

    @When("requesting the interaction limits for a the repo {string} of {string}")
    fun requestingTheInteractionLimitsForTheRepoOf(repo: String, owner: String) {
        interactionLimits = githubClient.requestInteractionLimitsForARepo(owner, repo)
    }

    @Then("all the user info should appear")
    fun allTheUserInfoShouldAppear() {
        log.info(githubUser.toString())
        assertThat(githubUser).isNotNull
    }

    @Then("the token should be created")
    fun theTokenShouldBeCreated() {
        log.info(registrationToken.toString())
        assertThat(registrationToken).isNotNull
    }

    @Then("the interaction limits should be answered")
    fun theInteractionLimitsShouldBeAnswered() {
        log.info(interactionLimits.toString())
        assertThat(interactionLimits).isNotNull
    }
}