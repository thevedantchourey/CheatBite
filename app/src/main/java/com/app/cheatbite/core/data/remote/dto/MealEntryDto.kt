package com.app.cheatbite.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MealEntryDto(
    val time: String = "",
    val food: String = ""
)

