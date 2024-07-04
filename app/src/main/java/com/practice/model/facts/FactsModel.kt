package com.practice.model.facts


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FactsModel(
    val __v: Int? = null,
    val _id: String? = null,
    val createdAt: String? = null,
    val deleted: Boolean? = null,
    val status: Status? = null,
    val text: String? = null,
    val type: String? = null,
    val updatedAt: String? = null,
    val user: String? = null
)