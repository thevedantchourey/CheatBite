package com.app.cheatbite.app.domain.repository

import android.content.Context
import com.app.cheatbite.app.data.mapper.toFoods
import com.app.cheatbite.app.data.mapper.toIntent
import com.app.cheatbite.app.domain.model.Foods
import com.app.cheatbite.app.domain.model.Intent
import com.app.cheatbite.core.data.local.dto.IntentDto
import com.app.cheatbite.core.data.local.source.LocalSnackSenseiDataSource
import com.app.cheatbite.core.domain.util.ParseCallError
import com.app.cheatbite.core.domain.util.Result
import com.app.cheatbite.core.domain.util.map
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.io.IOException

class SnackSenseiRepositoryImpl(
    private val context: Context,
    private val localDataSource: LocalSnackSenseiDataSource,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.IO
): SnackSenseiRepository {
    private var cachedIntents: Intent?  = null
    private var cachedDiet: Foods? = null
    override suspend fun parseIntent(): Intent {
        val jsonString = context.assets.open("intents.json")
            .bufferedReader()
            .use { it.readText() }
        cachedIntents?.let {
            return it
        }
       return withContext(defaultDispatcher){
           val dtoResult :  IntentDto = localDataSource.getIntents(jsonString = jsonString)
           val intentsResult = dtoResult.toIntent()
           cachedIntents = intentsResult
           intentsResult
       }
    }

    override suspend fun parseDietCSV(): Result<Foods, ParseCallError> {
        cachedDiet?.let { return Result.Success(it) }
        return withContext(defaultDispatcher){

            val dtoResult = try{
                context.assets.open("diet_foods.csv").use{stream->
                    localDataSource.parseDietCsv(stream)
                }
            }catch(e: IOException){
                return@withContext Result.Error(error = ParseCallError.FILE_NOT_FOUND_OR_EMPTY, e.message)
            }
            dtoResult.map {
                val domainData = it.toFoods()
                cachedDiet = domainData
                domainData
            }
        }
    }

    override suspend fun parseCheatMealCSV(): Result<Foods, ParseCallError>  {
        return withContext(defaultDispatcher){
            val dtoResult = try{
                context.assets.open("cheat_meals.csv").use{stream->
                    localDataSource.parseCheatMealCsv(stream)
                }
            }catch(e: IOException){
                return@withContext Result.Error(error = ParseCallError.FILE_NOT_FOUND_OR_EMPTY, e.message)
            }
            dtoResult.map { it.toFoods() }
        }
    }
}