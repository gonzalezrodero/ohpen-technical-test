package rest.jsonSchemas

import com.google.gson.annotations.SerializedName

data class RegistrationToken(
    @SerializedName("token")
    private val token: String,
    @SerializedName("expires_at")
    private val expires_at: String
)