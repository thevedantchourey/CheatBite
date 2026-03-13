package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.Intent
import com.app.cheatbite.core.data.local.dto.IntentDto

fun IntentDto.toIntent(): Intent {
    return Intent(
        intents = intents.map { it.toIntentItems() }
    )
}