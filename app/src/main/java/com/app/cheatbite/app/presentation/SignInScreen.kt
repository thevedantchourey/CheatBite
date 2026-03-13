package com.app.cheatbite.app.presentation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.app.cheatbite.R
import com.app.cheatbite.app.viewmodels.AuthViewModel
import com.app.cheatbite.ui.theme.OffBlack
import com.app.cheatbite.ui.theme.OffWhite
import com.app.cheatbite.ui.theme.TranslucentWhite
import com.app.cheatbite.ui.theme.dimens


@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    onToPreferences: () -> Unit = {},
    viewModel: AuthViewModel
){
    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(bottomEnd = 40.dp, bottomStart = 40.dp))
                .height(MaterialTheme.dimens.medium4).background(OffBlack),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text= "Sign-In",
                maxLines = 1,
                color = OffWhite,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.poppins_medium)),
            )
        }
        Spacer(modifier = Modifier.height(MaterialTheme.dimens.small2))
        Box(
            modifier = Modifier.fillMaxWidth()
                .height(MaterialTheme.dimens.medium4).weight(1f)
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .background(TranslucentWhite).border(2.dp, OffBlack, RoundedCornerShape(topEnd = 40.dp, topStart = 40.dp)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxSize().blur(3.dp).padding(top = MaterialTheme.dimens.medium1),
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
                    modifier = Modifier.fillMaxWidth().padding(MaterialTheme.dimens.small2)
                        .height(MaterialTheme.dimens.medium2 - 7.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(),
                        shape = RoundedCornerShape(MaterialTheme.dimens.small1),
                        colors = CardDefaults.cardColors(
                            containerColor = OffBlack
                        ),
                        onClick = {}
                    ) {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text= "Sign-up with Gmail",
                                maxLines = 1,
                                color = OffWhite,
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
