package com.app.cheatbite.core.data.local.source

import com.app.cheatbite.core.data.local.dto.FoodsDto
import com.app.cheatbite.core.data.local.dto.IntentDto
import com.app.cheatbite.core.domain.util.ParseCallError
import java.io.InputStream
import com.app.cheatbite.core.domain.util.Result

interface SnackSenseiDataSource {
    suspend fun getIntents(jsonString: String) : IntentDto
    suspend fun parseDietCsv(inputStream: InputStream): Result<FoodsDto, ParseCallError>
    suspend fun parseCheatMealCsv(inputStream: InputStream): Result<FoodsDto, ParseCallError>
}