package com.practice.model.facts


import androidx.annotation.Keep
import kotlinx.serialization.Serializable

@Keep
@Serializable
data class FactsModel(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)