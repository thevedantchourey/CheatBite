package com.app.cheatbite.app.presentation

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cheatbite.R
import com.app.cheatbite.app.Events
import com.app.cheatbite.app.presentation.components.forms.Info
import com.app.cheatbite.app.states.UserState
import com.app.cheatbite.app.viewmodels.AccountViewModel
import com.app.cheatbite.core.presentation.util.ObserveAsEvents
import com.app.cheatbite.core.presentation.util.getDrawableIdForProfile
import com.app.cheatbite.ui.theme.LightBlack
import com.app.cheatbite.ui.theme.LightLittlePink
import com.app.cheatbite.ui.theme.LightPink
import com.app.cheatbite.ui.theme.OffWhite
import com.app.cheatbite.ui.theme.TextPink
import com.app.cheatbite.ui.theme.TextGreen
import com.app.cheatbite.ui.theme.TextRed
import com.app.cheatbite.ui.theme.TranslucentWhite
import com.app.cheatbite.ui.theme.dimens
import kotlinx.coroutines.flow.distinctUntilChanged


private val days = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
private val avatars = listOf( "base_profile" ,"male_profile01", "male_profile02", "male_profile03", "female_profile01", "female_profile02", "female_profile03")

@Composable
fun PreferencesScreen(
    modifier: Modifier = Modifier,
    viewModel: AccountViewModel,
    onToHomeScreen: () -> Unit = {},
){
    val context = LocalContext.current
    val mealType by viewModel.mealType.collectAsStateWithLifecycle()
    val updateMealType =  viewModel::updateMealType
    val selectedAvatar by viewModel.avatar.collectAsStateWithLifecycle()
    val updateAvatar =  viewModel::updateAvatar
//    val subscription by viewModel.subscription.collectAsStateWithLifecycle()
//    val updateSubscription =  viewModel::updateSubscription
    val pagerState = rememberPagerState(initialPage = 0) { avatars.size }
    val isSetUpClicked = remember { mutableStateOf(false) }
    val associatedDay = remember { mutableStateOf("") }
    val userState by viewModel.userState.collectAsStateWithLifecycle()
    if(isSetUpClicked.value){
        Dialog(onDismissRequest = { isSetUpClicked.value = false }) {
            Info.DietPlan(
                isSetUpClicked = isSetUpClicked,
                viewModel = viewModel,
                day = associatedDay,
                screen = "preference"
            )
        }
    }

    when(userState){
        is UserState.Success -> {
            onToHomeScreen()
        }
        else -> Unit
    }

    ObserveAsEvents(events = viewModel.events) { event ->
        when(event){
            is Events.Error ->{
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            is Events.Success -> {
                Toast.makeText(
                    context,
                    event.message,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> { }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.currentPage }
            .distinctUntilChanged()
            .collect { currentJobIndex ->
                 updateAvatar.invoke(avatars[currentJobIndex])
            }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .clip(RoundedCornerShape(bottomStart = 40.dp, bottomEnd = 40.dp))
                .background(OffWhite),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Box(
                modifier = modifier.fillMaxWidth().height(MaterialTheme.dimens.medium2)
                    .padding(horizontal = MaterialTheme.dimens.small1)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.small2)),
            ){
                Image(
                    modifier = Modifier.fillMaxSize().blur(15.dp),
                    painter = painterResource(R.drawable.top_bar_bg),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(MaterialTheme.dimens.small2))
                        .background(LightPink)
                        .border(BorderStroke(1.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small2)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                            .padding(MaterialTheme.dimens.small1),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Account",
                            maxLines = 1,
                            color = Color.White,
                            fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            overflow = TextOverflow.Ellipsis
                        )
//                        Row(
//                            horizontalArrangement = Arrangement.spacedBy(10.dp),
//                            verticalAlignment = Alignment.CenterVertically
//                        ) {
//                            Column(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(25.dp)).height(26.dp)
//                                    .background(SkyPink).clickable { updateSubscription.invoke("pro") }
//                                    .border(BorderStroke(0.5.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small1)),
//                                verticalArrangement = Arrangement.Center,
//                            ) {
//                                Row(
//                                    modifier = Modifier.padding(horizontal = 10.dp),
//                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = "Pro",
//                                        color = TextPink,
//                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = MaterialTheme.typography.bodySmall.fontSize
//                                    )
//                                    if (subscription == "pro") {
//                                        Icon(
//                                            modifier = Modifier.size(MaterialTheme.dimens.extraSmall),
//                                            imageVector = Icons.Filled.Flare,
//                                            contentDescription = "Localized description",
//                                            tint = TextPink
//                                        )
//                                    }
//                                }
//                            }
//                            Column(
//                                modifier = Modifier
//                                    .clip(RoundedCornerShape(25.dp))
//                                    .height(26.dp)
//                                    .background(GlassGreen).border(BorderStroke(0.5.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small2)),
//                                verticalArrangement = Arrangement.Center,
//                            ) {
//                                Row(
//                                    modifier = Modifier.padding(horizontal = 10.dp),
//                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
//                                    verticalAlignment = Alignment.CenterVertically
//                                ) {
//                                    Text(
//                                        text = "Free",
//                                        color = TextGreen,
//                                        fontFamily = FontFamily(Font(R.font.poppins_regular)),
//                                        fontWeight = FontWeight.Bold,
//                                        fontSize = MaterialTheme.typography.bodySmall.fontSize
//                                    )
//                                    if (subscription == "free") {
//                                        Icon(
//                                            modifier = Modifier.size(MaterialTheme.dimens.extraSmall),
//                                            imageVector = Icons.Filled.Flare,
//                                            contentDescription = "Localized description",
//                                            tint = TextGreen
//                                        )
//                                    }
//                                }
//                            }
//                        }
//                        if (subscription == "pro") {
//                            Text(
//                                "buy a shake\uD83E\uDDCB$2",
//                                maxLines = 1,
//                                color = Color.White,
//                                fontSize = MaterialTheme.typography.bodySmall.fontSize,
//                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
//                                fontWeight = FontWeight.Bold,
//                                overflow = TextOverflow.Ellipsis
//                            )
//                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Slide through emojis\n to select your avatar.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if(mealType == "veg") TextGreen else TextRed,
                    textAlign = TextAlign.Center,
                    fontFamily = FontFamily(Font(R.font.poppins_regular)),
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(MaterialTheme.dimens.small1),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                Box(
                    modifier = Modifier.wrapContentSize(),
                    contentAlignment = Alignment.BottomStart
                ) {
                    Card(
                        modifier = Modifier
                            .padding(
                                start = if (mealType == "veg") 40.dp else 40.dp,
                                bottom = if (mealType == "veg") 80.dp else 88.dp
                            )
                            .rotate(-45f)
                            .height(MaterialTheme.dimens.medium1 + 2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        onClick = { updateMealType.invoke("non-veg") },
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 16.dp
                        ),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Row(
                            modifier = Modifier.padding(
                                top = 10.dp,
                                bottom = 10.dp,
                                start = MaterialTheme.dimens.small2,
                                end = MaterialTheme.dimens.small1
                            ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Non-Veg",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.poppins_bold)),
                                color = TextRed
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1 - 2.dp))
                            if (mealType == "non-veg") {
                                Icon(
                                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeSmall),
                                    imageVector = Icons.Outlined.ArrowOutward,
                                    contentDescription = "Localized description",
                                    tint = TextPink
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier
                            .padding(start = 70.dp, bottom = 20.dp)
                            .height(MaterialTheme.dimens.medium1 + 2.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White,
                        ),
                        onClick = { updateMealType.invoke("veg") },
                        shape = RoundedCornerShape(
                            topStart = 0.dp,
                            topEnd = 16.dp,
                            bottomStart = 0.dp,
                            bottomEnd = 16.dp
                        ),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        Row(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    top = 10.dp,
                                    bottom = 10.dp,
                                    start = MaterialTheme.dimens.small2,
                                    end = MaterialTheme.dimens.small1
                                ),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Veg",
                                style = MaterialTheme.typography.bodyMedium,
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF36B400)
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1 - 2.dp))
                            if (mealType == "veg") {
                                Icon(
                                    modifier = Modifier.size(MaterialTheme.dimens.logoSizeSmall),
                                    imageVector = Icons.Outlined.ArrowOutward,
                                    contentDescription = "Localized description",
                                    tint = TextPink
                                )
                            }
                        }
                    }
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.medium2 + 8.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Transparent,
                        ),
                        elevation = CardDefaults.cardElevation()
                    ) {
                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(0.dp),
                            key = { index -> avatars[index] }
                        ) { avatar ->
                            val avatarProfile = getDrawableIdForProfile(avatars[avatar])
                            Box(
                                modifier = Modifier
                                    .size(MaterialTheme.dimens.medium3)
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 30.dp,
                                            topEnd = 30.dp,
                                            bottomEnd = 12.dp,
                                            bottomStart = 12.dp
                                        )
                                    )
                                    .clickable {
                                        updateAvatar.invoke(avatars[avatar])
                                    },
                            ) {
                                Image(
                                    painter = painterResource(avatarProfile),
                                    contentDescription = "profile",
                                    contentScale = ContentScale.Crop,
                                )
                                if (selectedAvatar == avatars[avatar]) {
                                    Image(
                                        modifier = Modifier.clickable {
                                            updateAvatar.invoke(avatars[avatar])
                                        },
                                        painter = painterResource(R.drawable.selected_profile),
                                        contentDescription = "profile",
                                        contentScale = ContentScale.Crop,
                                    )
                                }
                            }
                        }
                    }
                }
                Card(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(MaterialTheme.dimens.small1 - 6.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = LightLittlePink
                    ),
                    border = BorderStroke(1.dp, TranslucentWhite),
                    onClick = { viewModel.saveAllPreferences() },
                    shape = RoundedCornerShape(MaterialTheme.dimens.small3 * 2)
                ) {
                    Row(
                        modifier = Modifier.padding(MaterialTheme.dimens.small1),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Go",
                            style = MaterialTheme.typography.titleMedium,
                            fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            color = Color.White
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Column(
            modifier = Modifier.fillMaxWidth().weight(1f)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(MaterialTheme.colorScheme.surface),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Box(
                modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimens.medium3 + 20.dp)
                    .padding(MaterialTheme.dimens.small1)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.small2)),
            ) {
                Image(
                    modifier = Modifier.fillMaxSize().blur(15.dp),
                    painter = painterResource(R.drawable.top_bar_bg),
                    contentDescription = "profile",
                    contentScale = ContentScale.Crop,
                )
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(MaterialTheme.dimens.small2))
                        .background(LightPink)
                        .border(BorderStroke(1.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small2)),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Card(
                            modifier = Modifier.weight(1f),
                            colors = CardDefaults.cardColors(
                                containerColor = LightPink
                            ),
                            shape = RoundedCornerShape(MaterialTheme.dimens.small2)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(MaterialTheme.dimens.small1),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "Week Diet",
                                    style = MaterialTheme.typography.bodyLarge,
                                    fontWeight = FontWeight.Bold,
                                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(days.chunked(2)) { daysGroup ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1),
                    ) {
                        daysGroup.forEach { day ->
                            val mealsForDay by when(day.lowercase()){
                                "monday" -> viewModel.monday.collectAsStateWithLifecycle()
                                "tuesday" -> viewModel.tuesday.collectAsStateWithLifecycle()
                                "wednesday" -> viewModel.wednesday.collectAsStateWithLifecycle()
                                "thursday" -> viewModel.thursday.collectAsStateWithLifecycle()
                                "friday" -> viewModel.friday.collectAsStateWithLifecycle()
                                "saturday" -> viewModel.saturday.collectAsStateWithLifecycle()
                                else -> viewModel.sunday.collectAsStateWithLifecycle()
                            }
                            Box(
                                modifier = Modifier
                                    .height(MaterialTheme.dimens.medium4).weight(1f)
                                    .clip(RoundedCornerShape(MaterialTheme.dimens.small2))
                                    .border(
                                        BorderStroke(1.dp, TranslucentWhite),
                                        RoundedCornerShape(MaterialTheme.dimens.small1)
                                    ),
                            ) {
                                Image(
                                    modifier = Modifier.fillMaxSize().blur(15.dp),
                                    painter = painterResource(R.drawable.top_bar_bg),
                                    contentDescription = "profile",
                                    contentScale = ContentScale.Crop,
                                )
                                Card(
                                    modifier = Modifier
                                        .clickable {
                                            isSetUpClicked.value = true
                                            associatedDay.value = day.lowercase()
                                        },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = LightBlack
                                    )
                                ) {
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth().wrapContentHeight()
                                            .padding(MaterialTheme.dimens.small1 - 6.dp),
                                        colors = CardDefaults.cardColors(
                                            containerColor = LightPink
                                        ),
                                        onClick = { },
                                        shape = RoundedCornerShape(MaterialTheme.dimens.small1),
                                        border = BorderStroke(0.2.dp, TranslucentWhite)
                                    ) {
                                        Row(
                                            modifier = Modifier.fillMaxWidth().padding(10.dp),
                                            verticalAlignment = Alignment.Top,
                                            horizontalArrangement = Arrangement.Start
                                        ) {
                                            Text(
                                                text = day,
                                                color = Color.White,
                                                style = MaterialTheme.typography.bodyMedium,
                                                fontWeight = FontWeight.Bold,
                                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                            )
                                            Spacer(modifier = Modifier.weight(1f))
                                            if (mealsForDay.isNotEmpty()) {
                                                Icon(
                                                    modifier = Modifier.size(MaterialTheme.dimens.small1),
                                                    imageVector = Icons.Filled.CheckCircle,
                                                    contentDescription = "Localized description",
                                                    tint = Color.White
                                                )
                                            } else {
                                                Icon(
                                                    modifier = Modifier.size(MaterialTheme.dimens.small1),
                                                    imageVector = Icons.Filled.RadioButtonUnchecked,
                                                    contentDescription = "Localized description",
                                                    tint = Color.White
                                                )
                                            }
                                        }
                                    }
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = if (mealsForDay.isNotEmpty()) "Meals added." else "click to set\n your diet\n for the day.",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.White,
                                            lineHeight = 14.sp,
                                            textAlign = TextAlign.Center,
                                            fontWeight = FontWeight.Bold,
                                            fontFamily = FontFamily(Font(R.font.poppins_regular)),
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
                }
            }
        }
    }
}
