package com.practice.model.facts


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class Status(
    val sentCount: Int? = null,
    val verified: String? = null
)