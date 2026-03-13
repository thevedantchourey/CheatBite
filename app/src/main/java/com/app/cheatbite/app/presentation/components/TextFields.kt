package com.app.cheatbite.app.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.cheatbite.R
import com.app.cheatbite.app.getImeAwarePadding
import com.app.cheatbite.ui.theme.LightBlack
import com.app.cheatbite.ui.theme.dimens


@Preview
@Composable
fun TextFields(
    mealTime: String = "",
    diet: String = "",
    onMealTimeChange: (String) -> Unit = {},
    onDietChange: (String) -> Unit = {}
){
    val adjustedPadding = getImeAwarePadding()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MaterialTheme.dimens.small1)
            .padding(bottom = adjustedPadding),
        horizontalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Meal Time",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = LightBlack,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                style = TextStyle(lineHeight = 16.sp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1 - 6.dp))
            TextField(
                value = mealTime,
                onValueChange = { onMealTimeChange.invoke(it) },
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        text = "Eg. Breakfast",
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
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Diet",
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                color = LightBlack,
                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Start,
                style = TextStyle(lineHeight = 16.sp)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1 - 6.dp))
            TextField(
                value = diet,
                onValueChange = { onDietChange.invoke(it) },
                shape = RoundedCornerShape(16.dp),
                placeholder = {
                    Text(
                        text = "Eg. Oats",
                        color = Color.White,
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
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
    }
}