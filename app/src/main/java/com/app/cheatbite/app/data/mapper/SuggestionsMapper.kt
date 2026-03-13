package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.Suggestions
import com.app.cheatbite.core.data.remote.dto.SuggestionsDto

fun SuggestionsDto.toSuggestions() : Suggestions {
    return Suggestions(
        suggestions = suggestions.map { it.toSuggestion() }
    )
}

fun Suggestions.toSuggestionsDto() : SuggestionsDto {
    return SuggestionsDto(
        suggestions = suggestions.map { it.toSuggestionDto() }
    )
}