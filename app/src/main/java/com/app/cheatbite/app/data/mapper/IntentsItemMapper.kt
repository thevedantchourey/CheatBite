package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.IntentItems
import com.app.cheatbite.core.data.local.dto.IntentItemsDto

fun IntentItemsDto.toIntentItems(): IntentItems {
    return IntentItems(
        tag = tag,
        patterns = patterns,
        responses = responses
    )
}