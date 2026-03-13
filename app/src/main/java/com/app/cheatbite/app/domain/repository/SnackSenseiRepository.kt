package com.app.cheatbite.app.domain.repository


import com.app.cheatbite.app.domain.model.Foods
import com.app.cheatbite.app.domain.model.Intent
import com.app.cheatbite.core.domain.util.ParseCallError
import com.app.cheatbite.core.domain.util.Result

interface SnackSenseiRepository {
    suspend fun parseIntent(): Intent
    suspend fun parseDietCSV(): Result<Foods, ParseCallError>
    suspend fun parseCheatMealCSV(): Result<Foods, ParseCallError>
}
