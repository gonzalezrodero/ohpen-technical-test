package rest.jsonSchemas

import com.google.gson.annotations.SerializedName

data class GithubUser(
    @SerializedName("login")
    private val login: String,
    @SerializedName("id")
    private val id: String,
    @SerializedName("node_id")
    val node_id: String,
    @SerializedName("avatar_url")
    private val avatar_url: String,
    @SerializedName("gravatar_id")
    private val gravatar_id: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("html_url")
    private val html_url: String,
    @SerializedName("followers_url")
    private val followers_url: String,
    @SerializedName("following_url")
    val following_url: String,
    @SerializedName("gists_url")
    private val gists_url: String,
    @SerializedName("starred_url")
    private val starred_url: String,
    @SerializedName("subscriptions_url")
    val subscriptions_url: String,
    @SerializedName("organizations_url")
    private val organizations_url: String,
    @SerializedName("repos_url")
    private val repos_url: String,
    @SerializedName("events_url")
    val events_url: String,
    @SerializedName("received_events_url")
    private val received_events_url: String,
    @SerializedName("type")
    private val type: String,
    @SerializedName("site_admin")
    private val site_admin: Boolean,
    @SerializedName("name")
    val name: String,
    @SerializedName("company")
    private val company: String,
    @SerializedName("blog")
    val blog: String,
    @SerializedName("location")
    private val location: String,
    @SerializedName("email")
    private val email: String,
    @SerializedName("hireable")
    val hireable: Boolean,
    @SerializedName("bio")
    private val bio: String,
    @SerializedName("twitter_username")
    private val twitter_username: String,
    @SerializedName("public_repos")
    private val public_repos: Int,
    @SerializedName("public_gists")
    val public_gists: Int,
    @SerializedName("followers")
    private val followers: Int,
    @SerializedName("following")
    val following: Int,
    @SerializedName("created_at")
    private val created_at: String,
    @SerializedName("updated_at")
    private val updated_at: String
)