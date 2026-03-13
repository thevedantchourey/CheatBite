package com.app.cheatbite.app.presentation

import android.widget.Toast
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.app.cheatbite.R
import com.app.cheatbite.app.states.AuthState
import com.app.cheatbite.app.viewmodels.AuthViewModel
import com.app.cheatbite.core.presentation.util.ObserveAsEvents
import com.app.cheatbite.ui.theme.TranslucentWhite
import com.app.cheatbite.ui.theme.dimens
import com.app.cheatbite.app.Events
import com.app.cheatbite.ui.theme.LightPink
import com.app.cheatbite.ui.theme.OffWhite


@Composable
fun GateWayScreen(
    modifier: Modifier = Modifier,
    onToPreferenceScreen: () -> Unit = {},
    onToHomeScreen: () -> Unit = {},
    viewModel: AuthViewModel
){
    val activity = LocalActivity.current
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    LaunchedEffect(authState) {
        when(authState){
            is AuthState.Success -> {
                onToPreferenceScreen()
                viewModel.resetAuthState()
            }
            is AuthState.LoggedIn -> {
                onToHomeScreen()
                viewModel.resetAuthState()
            }
            else -> Unit
        }
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
            else ->{}
        }
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(OffWhite),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                .height(MaterialTheme.dimens.medium4)
                .background(LightPink)
                .border(1.dp, TranslucentWhite, RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(15.dp),
                painter = painterResource(id = R.drawable.signup_bg),
                contentDescription = "Background",
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row{
                    Text(
                        text= "CheatBite",
                        maxLines = 1,
                        color = Color.White,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        fontFamily = FontFamily(Font(R.font.poppins_medium)),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(MaterialTheme.dimens.medium4)
                .weight(1f)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(LightPink)
                .border(1.dp, TranslucentWhite, RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .fillMaxSize()
                    .blur(15.dp)
                    .padding(top = MaterialTheme.dimens.medium1),
                painter = painterResource(id = R.drawable.signin_bg),
                contentDescription = "Background",
                contentScale = ContentScale.Fit
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.dimens.small2)
                        .height(MaterialTheme.dimens.medium2 - 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(topEnd = MaterialTheme.dimens.small1, topStart = MaterialTheme.dimens.small1, bottomEnd = 40.dp, bottomStart = 40.dp),
                        colors = CardDefaults.cardColors(
                            containerColor =  LightPink
                        ),
                        border = BorderStroke(1.dp, TranslucentWhite),
                        onClick = { if (activity != null) {
                            viewModel.signInWithGoogle(activity)
                        } else { Toast.makeText(context, "Something went wrong.", Toast.LENGTH_SHORT).show() } }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text= "Sign-up with Gmail",
                                maxLines = 1,
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                fontFamily = FontFamily(Font(R.font.poppins_medium)),
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(MaterialTheme.dimens.small1))
        }
    }
}
