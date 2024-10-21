package com.example.getirlite.view.fragments.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.model.extension.double
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OnboardingSheetView(sheetState: SheetState, onDismissRequest: () -> Unit) {
    val accountManager: AccountManager = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val isLoggedIn by accountManager.isLoggedIn.collectAsStateWithLifecycle()

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(horizontal = Dp.margin)
        ) {
            SimpleText(
                text = stringResource(R.string.letsStart),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Blue
            )

            SimpleText(
                text = stringResource(R.string.onboardingSheetExplanation),
                fontSize = 14.sp,
                textAlign = TextAlign.Center
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = Color.Blue,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        onClick = {
                            accountManager.googleLogin()
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.google),
                    contentDescription = "",
                    modifier = Modifier
                        .size(20.dp)
                )

                SimpleText(
                    text = stringResource(R.string.signWithGoogle),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Blue
                )

                Spacer(modifier = Modifier.weight(1f))
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                HorizontalDivider(
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(0.4f)
                        .height(1.dp)
                )

                SimpleText(
                    text = stringResource(id = R.string.or),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(0.2f)
                )

                HorizontalDivider(
                    color = Color.Gray,
                    modifier = Modifier
                        .weight(0.4f)
                        .height(1.dp)
                )
            }

            SimpleText(
                text = stringResource(R.string.continue_as_guest),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier
                    .padding(top = Dp.padding.double)
                    .clickable(
                        onClick = {
                            accountManager.signInAnonymously()
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )

            Spacer(modifier = Modifier.size(100.dp))
        }
    }
}