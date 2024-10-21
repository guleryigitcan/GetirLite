package com.example.getirlite.view.fragments.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.double
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingView(navController: NavController) {
    val accountManager: AccountManager = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val isLoggedIn by accountManager.isLoggedIn.collectAsStateWithLifecycle()

    var showBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lottie_onboarding))

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LottieAnimation(
            composition = composition,
            iterations = LottieConstants.IterateForever,
            isPlaying = true,
            modifier = Modifier
                .padding(top = Dp.padding.double)
                .height(300.dp)
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .padding(horizontal = Dp.margin, vertical = 8.dp)
                .fillMaxWidth()
                .height(height = 50.dp)
                .background(
                    color = Color.Blue,
                    shape = RoundedCornerShape(12.dp)
                )
                .clickable(
                    onClick = {
                        showBottomSheet = true
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
        ) {
            SimpleText(
                text = stringResource(R.string.start),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }

        SimpleText(
            text = stringResource(R.string.continue_as_guest),
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
            textAlign = TextAlign.Center,
            color = Color.Gray,
            modifier = Modifier
                .padding(top = 8.dp, bottom = Screen.Dimensions.bottomBar)
                .clickable(
                    onClick = {
                        User.didSeeOnboarding.set(true)
                        accountManager.signInAnonymously()
                        if (isLoggedIn) {
                            User.didSeeOnboarding.set(true)
                            navController.navigate(NavigationItem.ProductList.route)
                        }
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
        )
    }

    if (showBottomSheet) {
        if (isLoggedIn) {
            showBottomSheet = false
            User.didSeeOnboarding.set(true)
            navController.navigate(NavigationItem.ProductList.route)
        }
        OnboardingSheetView (sheetState = sheetState) {
            showBottomSheet = false
        }
    }

}