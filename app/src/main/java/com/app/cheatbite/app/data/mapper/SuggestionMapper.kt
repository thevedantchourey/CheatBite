package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.Suggestion
import com.app.cheatbite.core.data.remote.dto.SuggestionDto

fun SuggestionDto.toSuggestion() : Suggestion{
    return Suggestion(
        food = food.toFoodItem(),
        matchScore = matchScore
    )
}

fun Suggestion.toSuggestionDto() : SuggestionDto {
    return SuggestionDto(
        food = food.toFoodItemDto(),
        matchScore = matchScore
    )
}