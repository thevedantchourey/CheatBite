package com.app.cheatbite.core.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class SuggestionsDto(
    val suggestions:  List<SuggestionDto> = emptyList()
)