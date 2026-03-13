package com.app.cheatbite.core.data.local.source

import com.app.cheatbite.core.data.local.dto.FoodItemDto
import com.app.cheatbite.core.data.local.dto.FoodsDto
import com.app.cheatbite.core.data.local.dto.IntentDto
import com.app.cheatbite.core.data.networking.localSafeCall
import com.app.cheatbite.core.domain.util.ParseCallError
import kotlinx.serialization.json.Json
import java.io.InputStream
import com.app.cheatbite.core.domain.util.Result
import kotlinx.io.IOException


class LocalSnackSenseiDataSource: SnackSenseiDataSource {
    private val jsonParser = Json { ignoreUnknownKeys = true }

    override suspend fun getIntents(jsonString: String): IntentDto {
        return jsonParser.decodeFromString<IntentDto>(jsonString)
    }

    override suspend fun parseDietCsv(inputStream: InputStream): Result<FoodsDto, ParseCallError> {
        return parseCsvInternal(inputStream)
    }

    override suspend fun parseCheatMealCsv(inputStream: InputStream): Result<FoodsDto, ParseCallError> {
        return parseCsvInternal(inputStream)
    }

    private fun parseCsvInternal(inputStream: InputStream): Result<FoodsDto, ParseCallError> {
        return localSafeCall {
            val reader = inputStream.bufferedReader()
            val headerLine = reader.readLine() ?: throw IOException("CSV file is empty or invalid.")
            val header = headerLine.split(',')

            FoodsDto(reader.lineSequence()
                .filter { it.isNotBlank() }
                .map { line ->
                    val values = line.split(',')
                    val foodMap = header.zip(values.padEnd(header.size, "")).toMap()
                    FoodItemDto(
                        foodItemName = foodMap["food_item"]?.lowercase()?.trim() ?: "",
                        calories = foodMap["calories"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        protein = foodMap["protein"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        carbs = foodMap["carbs"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        fats = foodMap["fats"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        saturatedFat = foodMap["saturated_fat"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        fiber = foodMap["fiber"]?.trim()?.toDoubleOrNull() ?: 0.0,
                        type = foodMap["type"]?.lowercase()?.trim() ?: "veg"
                    )
                }.toList()
            )
        }
    }

    private fun List<String>.padEnd(size: Int, value: String): List<String> {
        if (this.size >= size) return this
        return this + List(size - this.size) { value }
    }
}