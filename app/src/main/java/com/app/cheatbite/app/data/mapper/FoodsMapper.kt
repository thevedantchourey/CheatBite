package com.app.cheatbite.app.data.mapper

import com.app.cheatbite.app.domain.model.Foods
import com.app.cheatbite.core.data.local.dto.FoodsDto

fun FoodsDto.toFoods(): Foods {
    return Foods(
        foodItems = foodItems.map { it.toFoodItem()}
    )
}