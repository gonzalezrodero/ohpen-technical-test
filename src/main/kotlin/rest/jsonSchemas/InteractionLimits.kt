package rest.jsonSchemas

import com.google.gson.annotations.SerializedName

data class InteractionLimits(
    @SerializedName("limit")
    private val limit: String,
    @SerializedName("origin")
    private val origin: String,
    @SerializedName("expires_at")
    private val expires_at: String
)