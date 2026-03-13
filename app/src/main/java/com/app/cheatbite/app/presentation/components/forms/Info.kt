package com.app.cheatbite.app.presentation.components.forms

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cheatbite.R
import com.app.cheatbite.app.domain.model.MealEntry
import com.app.cheatbite.app.presentation.components.TextFields
import com.app.cheatbite.app.viewmodels.AccountViewModel
import com.app.cheatbite.ui.theme.LightBlack
import com.app.cheatbite.ui.theme.GlassRed
import com.app.cheatbite.ui.theme.OffWhite
import com.app.cheatbite.ui.theme.dimens


object Info {
    @Composable
    fun DietPlan(
        isSetUpClicked: MutableState<Boolean>,
        viewModel: AccountViewModel,
        day: MutableState<String>,
        screen: String
    ){

        val updateMealTime =  viewModel::updateMealTime
        val updateDiet =  viewModel::updateDiet
        val mealTime by viewModel.mealTime.collectAsStateWithLifecycle()
        val diet by viewModel.diet.collectAsStateWithLifecycle()
        val mealsForDay by when(day.value){
            "monday" -> viewModel.monday.collectAsStateWithLifecycle()
            "tuesday" -> viewModel.tuesday.collectAsStateWithLifecycle()
            "wednesday" -> viewModel.wednesday.collectAsStateWithLifecycle()
            "thursday" -> viewModel.thursday.collectAsStateWithLifecycle()
            "friday" -> viewModel.friday.collectAsStateWithLifecycle()
            "saturday" -> viewModel.saturday.collectAsStateWithLifecycle()
            else -> viewModel.sunday.collectAsStateWithLifecycle()
        }

        val addMeals = when(day.value){
            "monday" -> viewModel::addMondayMeals
            "tuesday" -> viewModel::addTuesdayMeals
            "wednesday" -> viewModel::addWednesdayMeals
            "thursday" -> viewModel::addThursdayMeals
            "friday" -> viewModel::addFridayMeals
            "saturday" -> viewModel::addSaturdayMeals
            else -> viewModel::addSundayMeals
        }

        val removeMeal = when(day.value){
            "monday" -> viewModel::removeMondayMeals
            "tuesday" -> viewModel::removeTuesdayMeals
            "wednesday" -> viewModel::removeWednesdayMeals
            "thursday" -> viewModel::removeThursdayMeals
            "friday" -> viewModel::removeFridayMeals
            "saturday" -> viewModel::removeSaturdayMeals
            else -> viewModel::removeSundayMeals
        }

        Column(
            modifier = Modifier
                .width(MaterialTheme.dimens.large3).clip(RoundedCornerShape(20.dp))
                .background(OffWhite).border(BorderStroke(4.dp, LightBlack),RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge).clickable{isSetUpClicked.value = false},
                    colors = CardDefaults.cardColors(
                        containerColor = LightBlack
                    ),
                    shape = RoundedCornerShape(MaterialTheme.dimens.small1)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            }
            Card(
                modifier = Modifier.fillMaxWidth()
                    .padding( MaterialTheme.dimens.small1),
                colors = CardDefaults.cardColors(
                    containerColor = LightBlack
                ),
                shape = RoundedCornerShape(MaterialTheme.dimens.small1)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .padding(MaterialTheme.dimens.small1),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ){
                    if(mealsForDay.isNotEmpty()) {
                        mealsForDay.forEach {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    modifier = Modifier.size(MaterialTheme.dimens.small1)
                                        .clickable{ removeMeal.invoke(it) },
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Localized description",
                                    tint = GlassRed
                                )
                                Text(
                                    text = "${it.time}, ${it.food}",
                                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                    color = Color.White,
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Start,
                                    style = TextStyle(lineHeight = 16.sp)
                                )
                            }
                        }
                    }else{
                        Text(
                            text = "No diet plan added.",
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Start,
                            style = TextStyle(lineHeight = 16.sp)
                        )
                    }
                }
            }
            TextFields(
                mealTime = mealTime,
                diet = diet,
                onMealTimeChange = updateMealTime,
                onDietChange = updateDiet
            )
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Text(
                    text = if(mealsForDay.isNotEmpty()) "You can remove your\nmeal with 'x'." else "Add your diet plan.",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = LightBlack,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    style = TextStyle(lineHeight = 16.sp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                        .clickable {
                            addMeals.invoke(MealEntry(time= mealTime, food = diet))
                            updateMealTime.invoke("")
                            updateDiet.invoke("")
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = LightBlack
                    ),
                    shape = RoundedCornerShape(MaterialTheme.dimens.small1)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
                if(screen == "account") {
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                            .clickable {
                                viewModel.updateDay(day.value)
                                viewModel.updateDayDiet()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = LightBlack
                        ),
                        shape = RoundedCornerShape(MaterialTheme.dimens.small1)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Done,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun UpdateUsername(
        isUsernameClicked: MutableState<Boolean>,
        viewModel: AccountViewModel,
    ){
        val username by  viewModel.username.collectAsStateWithLifecycle()
        val updateMealType =  viewModel::updateUsername

        Column(
            modifier = Modifier
                .width(MaterialTheme.dimens.large3).clip(RoundedCornerShape(20.dp))
                .background(OffWhite).border(BorderStroke(4.dp, LightBlack),RoundedCornerShape(20.dp)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Username",
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    color = LightBlack,
                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    style = TextStyle(lineHeight = 16.sp)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1 - 6.dp))
                TextField(
                    value = username,
                    onValueChange = { updateMealType.invoke(it) },
                    shape = RoundedCornerShape(16.dp),
                    placeholder = {
                        Text(
                            text = "Eg. John Doe",
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            maxLines = 1
                        )
                    },
                    textStyle = MaterialTheme.typography.bodyMedium,
                    colors = TextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        focusedContainerColor = LightBlack,
                        unfocusedContainerColor = LightBlack,
                        cursorColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                    )
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ){
                Spacer(modifier = Modifier.weight(1f))
                Card(
                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                        .clickable {
                            isUsernameClicked.value = false
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = LightBlack
                    ),
                    shape = RoundedCornerShape(MaterialTheme.dimens.small1)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
                Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                Card(
                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                        .clickable {
                            viewModel.saveAllPreferences()
                            isUsernameClicked.value = false
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = LightBlack
                    ),
                    shape = RoundedCornerShape(MaterialTheme.dimens.small1)
                ) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = "Localized description",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}