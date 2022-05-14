package com.thomaskioko.tvmaniac.remote.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrailersResponse(

    @SerialName("id") val id: Int,
    @SerialName("results") val results: List<TrailerResponse>
)
