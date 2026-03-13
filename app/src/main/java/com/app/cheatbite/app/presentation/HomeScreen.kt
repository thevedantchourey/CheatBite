package com.app.cheatbite.app.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.DeleteOutline
import androidx.compose.material.icons.filled.ThumbDownOffAlt
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.filled.ThumbUpOffAlt
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cheatbite.R
import com.app.cheatbite.app.domain.entity.Sender
import com.app.cheatbite.app.domain.model.ChatMessage
import com.app.cheatbite.app.viewmodels.MainViewModel
import com.app.cheatbite.core.presentation.util.getDrawableIdForProfile
import com.app.cheatbite.ui.theme.DarkPink
import com.app.cheatbite.ui.theme.LightBlack
import com.app.cheatbite.ui.theme.LightLittlePink
import com.app.cheatbite.ui.theme.LightPink
import com.app.cheatbite.ui.theme.TranslucentWhite
import com.app.cheatbite.ui.theme.dimens
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel,
    onToAccount: () -> Unit = {},
){
    val userData by viewModel.user.collectAsStateWithLifecycle()
    val avatar = getDrawableIdForProfile(userData.avatar)
    val chatState by viewModel.chatState.collectAsStateWithLifecycle()
    val liked by viewModel.liked.collectAsStateWithLifecycle()
    val updateLiked = {isLiked: Boolean -> viewModel.updateLiked(isLiked)}
    val messages = chatState.messages
    var userInput by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val frames = listOf(R.drawable.animation_01, R.drawable.animation_02, R.drawable.animation_03, R.drawable.animation_04, R.drawable.animation_05, R.drawable.animation_01)
    val currentFrame = remember { mutableIntStateOf(0) }
    val listOfQuestion = listOf(
        "I'm craving a cheat breakfast! 🥞 Any ideas?",
        "What's a good cheat meal for today? Feeling hungry! 🍕",
        "Hit me with your best cheat meal suggestion! 🙌",
    )


    LaunchedEffect(Unit) {
        while (true) {
            delay(2000L)
            for (i in frames.indices) {
                currentFrame.intValue = i
                delay(50L)
            }
        }
    }

    LaunchedEffect(liked) {
        if (liked) {
            viewModel.updateUserData()
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                .background(MaterialTheme.colorScheme.surface).weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
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
                    Text(
                        "CheatBite",
                        modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1),
                        maxLines = 1,
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                        overflow = TextOverflow.Ellipsis,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }
                Card(
                    modifier = Modifier.size(MaterialTheme.dimens.medium4).align(Alignment.CenterEnd),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Transparent,
                    ),
                    elevation = CardDefaults.cardElevation(),
                    onClick = { onToAccount() }
                ) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        painter = painterResource(avatar),
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop,
                    )
                }
            }
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                if (messages.isEmpty()){
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Snack Sensei is here to\nsave the day.",
                            color = DarkPink,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            )
                        )
                        Text(
                            text = "Snack Sensei is here to\nsave the day.",
                            color = TranslucentWhite,
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                drawStyle = Stroke(
                                    width = 1f,
                                    join = StrokeJoin.Round
                                )
                            )
                        )
                    }
                }else{
                    LazyColumn(
                        state = listState,
                        modifier = Modifier.weight(1f),
                    ) {
                        itemsIndexed(messages) { index, message ->
                            val isLastMessage = index == messages.lastIndex
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.dimens.small1),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                MessageBubble(message = message, chatState.showFeedbackButtons, liked = liked,updateLiked = updateLiked, isLastMessage = isLastMessage)
                            }
                        }
                        item {
                            if (chatState.isBotTyping) {
                                Row(
                                    modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small1),
                                    horizontalArrangement = Arrangement.Start,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Card(
                                        modifier = Modifier.wrapContentSize(),
                                        shape = CircleShape,
                                        colors = CardDefaults.cardColors(
                                            containerColor = Color.Transparent,
                                        )
                                    ) {
                                        Image(
                                            modifier = Modifier.size(MaterialTheme.dimens.buttonHeight),
                                            painter = painterResource(frames[currentFrame.intValue]),
                                            contentScale = ContentScale.Crop,
                                            contentDescription = "Share"
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                                    Card(
                                        modifier = Modifier.wrapContentSize(),
                                        shape = CircleShape,
                                        colors = CardDefaults.cardColors(
                                            containerColor = LightBlack,
                                        ),
                                        border = BorderStroke(1.dp, TranslucentWhite)
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1, vertical = 10.dp),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = "Sensei is Cooking...",
                                                style = MaterialTheme.typography.bodyLarge,
                                                color = Color.White,
                                                lineHeight = 18.sp,
                                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    LaunchedEffect(chatState.messages.size) {
                        if (chatState.messages.isNotEmpty()) {
                            coroutineScope.launch {
                                listState.animateScrollToItem(0)
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Column(
            modifier = Modifier.fillMaxWidth().height(MaterialTheme.dimens.large1 - 115.dp)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .padding(MaterialTheme.dimens.small1)
                    .clip(RoundedCornerShape(MaterialTheme.dimens.small3))
                    .background(LightPink)
                    .border(BorderStroke(1.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small3)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.dimens.small1),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = userInput,
                        onValueChange = { userInput = it },
                        textStyle = TextStyle(
                            color = Color.White,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
                            fontFamily = FontFamily(Font(R.font.poppins_regular))
                        ),
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        placeholder = {
                            Text(
                                text = "Ask...",
                                textAlign = TextAlign.Start,
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                lineHeight = 42.sp,
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                                color = TranslucentWhite,
                            )
                        },
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Search
                        ),
                        keyboardActions = KeyboardActions(
                            onSearch = {}
                        ),
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            cursorColor = Color.White,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                        )
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                            .clickable {
                                if(messages.isNotEmpty()) {
                                    viewModel.resetChat()
                                }
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = LightPink
                        ),
                        shape = RoundedCornerShape(MaterialTheme.dimens.logoSizeLarge),
                        border = BorderStroke(0.2.dp, TranslucentWhite)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(MaterialTheme.dimens.logoSizeSmall),
                                imageVector = Icons.Filled.DeleteOutline,
                                contentDescription = "Localized description",
                                tint = if(messages.isNotEmpty()) Color.White else Color.DarkGray
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                            .clickable {
                                viewModel.snackSensei(userInput)
                                userInput = ""
                                keyboardController?.hide()
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = LightPink
                        ),
                        shape = RoundedCornerShape(MaterialTheme.dimens.logoSizeLarge),
                        border = BorderStroke(0.2.dp, TranslucentWhite)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                modifier = Modifier.size(MaterialTheme.dimens.logoSizeSmall),
                                imageVector = Icons.Filled.ArrowUpward,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
            LazyRow(
                modifier = Modifier.fillMaxWidth().padding(horizontal = MaterialTheme.dimens.small1),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.dimens.small1),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(
                    items = listOfQuestion,
                ){ questionOption ->
                    Box(
                        modifier = Modifier
                            .widthIn(max = 260.dp).height(MaterialTheme.dimens.medium2).clip(RoundedCornerShape(MaterialTheme.dimens.small1))
                            .background(LightLittlePink)
                            .border(BorderStroke(1.dp, TranslucentWhite), RoundedCornerShape(MaterialTheme.dimens.small1)).clickable{
                                viewModel.snackSensei(questionOption)
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize().padding(MaterialTheme.dimens.small1),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ){
                            Text(
                                text = questionOption,
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge,
                                fontFamily = FontFamily(Font(R.font.poppins_bold))
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun MessageBubble(message: ChatMessage, feedback: Boolean, liked: Boolean, updateLiked: (Boolean) -> Unit = {}, isLastMessage: Boolean){
    val alignment = if (message.sender == Sender.USER) Arrangement.End else Arrangement.Start
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.dimens.small1),
        horizontalArrangement = alignment,
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (message.sender == Sender.BOT){
            Card(
                modifier = Modifier.wrapContentSize(),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Image(
                    modifier = Modifier.size(MaterialTheme.dimens.medium1 + 12.dp),
                    painter = painterResource(R.drawable.snack_sensei),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Share"
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
        }
        Card(
            modifier = Modifier.weight(1f, fill = false).wrapContentSize(),
            shape = RoundedCornerShape(MaterialTheme.dimens.small3),
            colors = CardDefaults.cardColors(
                containerColor = LightBlack,
            ),
            border = BorderStroke(1.dp, TranslucentWhite)
        ) {
            Row(
                modifier = Modifier.padding(horizontal = MaterialTheme.dimens.small1, vertical = 12.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.poppins_medium)),
                )
            }
            if(feedback && message.sender == Sender.BOT && isLastMessage){
                Row(
                    modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small1),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Spacer(modifier = Modifier.weight(1f))
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                            .clickable {
                                updateLiked.invoke(true)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = LightBlack
                        ),
                        shape = RoundedCornerShape(MaterialTheme.dimens.small1),
                        border = BorderStroke(0.2.dp, TranslucentWhite)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if(liked) Icons.Filled.ThumbUp else Icons.Filled.ThumbUpOffAlt,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
                    Card(
                        modifier = Modifier.size(MaterialTheme.dimens.logoSizeLarge)
                            .clickable {
                                updateLiked.invoke(false)
                            },
                        colors = CardDefaults.cardColors(
                            containerColor = LightBlack
                        ),
                        shape = RoundedCornerShape(MaterialTheme.dimens.small1),
                        border = BorderStroke(0.2.dp, TranslucentWhite)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ThumbDownOffAlt,
                                contentDescription = "Localized description",
                                tint = Color.White
                            )
                        }
                    }
                }
            }
        }
        if (message.sender == Sender.USER){
            Spacer(modifier = Modifier.width(MaterialTheme.dimens.small1))
            Card(
                modifier = Modifier.wrapContentSize(),
                shape = CircleShape,
                colors = CardDefaults.cardColors(
                    containerColor = Color.Transparent,
                )
            ) {
                Image(
                    modifier = Modifier.size(MaterialTheme.dimens.medium1 + 12.dp),
                    painter = painterResource(R.drawable.male_profile01),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Share"
                )
            }
        }
    }
}
