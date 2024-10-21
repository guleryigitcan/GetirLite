package com.example.getirlite.view.fragments.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.getirlite.MainActivity
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.onboarding.OnboardingSheetView
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.zero
import com.example.getirlite.ui.theme.Divider
import com.example.getirlite.ui.theme.interpolate
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.onboarding.AccountManager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(navController: NavController) {
    val cartViewModel: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val accountManager: AccountManager = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val isLoggedIn by accountManager.isLoggedIn.collectAsStateWithLifecycle()

    var userName by remember { mutableStateOf(User.userName.string) }
    var email by remember { mutableStateOf(User.email.string) }
    var phoneNumber by remember { mutableStateOf(User.phone.string) }
    var isEditable by remember { mutableStateOf(false) }

    val sheetState = rememberModalBottomSheetState()
    var showHelpSheet by remember { mutableStateOf(false) }
    var showLogInSheet by remember { mutableStateOf(false) }

    @Composable
    fun LazyItemScope.buttonItemView(item: ProfileItem, index: Int, count: Int) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable(
                        onClick = {
                            if (item == ProfileItem.help) showHelpSheet = true
                            if (item.route.isNotEmpty()) navController.navigate(item.route)
                            else if (item == ProfileItem.login) showLogInSheet = true
                            else if (item == ProfileItem.logout) {
                                accountManager.signOut()
                                cartViewModel.clearCart()
                                navController.navigate(NavigationItem.Onboarding.route)
                            }
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            ) {
                Image(
                    imageVector = item.imageVector,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Blue),
                    modifier = Modifier
                        .padding(Dp.padding)
                        .size(24.dp)
                )
                if (item.isUserInfo)
                    BasicTextField(
                        value = if (item == ProfileItem.email) email else phoneNumber,
                        onValueChange = {
                            if (item == ProfileItem.email){
                                email = it
                                User.email.set(it)
                            }
                            else {
                                phoneNumber = it
                                User.phone.set(it)
                            }
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp
                        ),
                        enabled = isEditable && item.isUserInfo,
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = userName,
                                leadingIcon = null,
                                trailingIcon = null,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                innerTextField = {
                                    Box(
                                        contentAlignment = Alignment.CenterStart,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                placeholder = {},
                                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(0.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.White.interpolate(Color.Gray, fraction = 0.3f),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    cursorColor = Color.Transparent,//Color.Blue.interpolate(Color.Black, fraction = 0.5f),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLeadingIconColor = Color.Blue,
                                    unfocusedLeadingIconColor = Color.Blue,
                                    focusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                                    unfocusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                                    focusedPlaceholderColor = Color.White,
                                    unfocusedPlaceholderColor = Color.White,
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = if (item == ProfileItem.email) KeyboardType.Email else KeyboardType.Phone
                        )
                    )
                else
                    SimpleText(
                        text = if (item.isButton) item.title else if (item == ProfileItem.email) User.email.string else User.phone.string,
                        textAlign = TextAlign.Start,
                        color = if (item.isButton) Color.Black else Color.DarkGray
                    )

                Spacer(modifier = Modifier.weight(1f))

                if (item.isButton)
                    Image(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Gray),
                        modifier = Modifier
                            .padding(Dp.padding)
                            .size(24.dp)
                            .rotate(180f)
                    )
            }

            if (index < count - 1) HorizontalDivider(color = Color.Divider)
        }
    }

    @Composable
    fun LazyItemScope.profileNameView(item: ProfileItem) {
        Box(
            contentAlignment = Alignment.TopEnd,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
            ) {
                Image(
                    imageVector = item.imageVector,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Blue),
                    modifier = Modifier
                        .padding(Dp.padding)
                        .size(64.dp)
                )
//                if (isEditable)
                    BasicTextField(
                        value = userName,
                        onValueChange = {
                            userName = it
                            User.userName.set(it)
                        },
                        textStyle = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        ),
                        enabled = isEditable,
                        decorationBox = { innerTextField ->
                            TextFieldDefaults.DecorationBox(
                                value = userName,
                                leadingIcon = null,
                                trailingIcon = null,
                                enabled = true,
                                singleLine = true,
                                visualTransformation = VisualTransformation.None,
                                innerTextField = {
                                    Box(
                                        contentAlignment = Alignment.CenterStart,
                                        modifier = Modifier.fillMaxSize()
                                    ) {
                                        innerTextField()
                                    }
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                placeholder = {},
                                contentPadding = TextFieldDefaults.contentPaddingWithoutLabel(0.dp),
                                colors = TextFieldDefaults.colors(
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.White.interpolate(Color.Gray, fraction = 0.3f),
                                    focusedContainerColor = Color.White,
                                    unfocusedContainerColor = Color.White,
                                    cursorColor = Color.Transparent,//Color.Blue.interpolate(Color.Black, fraction = 0.5f),
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    focusedLeadingIconColor = Color.Blue,
                                    unfocusedLeadingIconColor = Color.Blue,
                                    focusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                                    unfocusedTrailingIconColor = Color.Gray.interpolate(Color.White, 0.3f),
                                    focusedPlaceholderColor = Color.White,
                                    unfocusedPlaceholderColor = Color.White,
                                )
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            capitalization = KeyboardCapitalization.Words
                        )
                    )
            }


            Image(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Blue),
                modifier = Modifier
                    .padding(end = 8.dp, top = 4.dp)
                    .size(32.dp)
                    .shadow(elevation = 16.dp)
                    .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                    .padding(8.dp)
                    .clickable(
                        onClick = {
                            isEditable = !isEditable
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )
        }
    }

    @Composable
    fun body() {
        Column {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dp.zero),
            ) {
                item { Spacer(modifier = Modifier.size(Screen.Dimensions.topBar)) }

                itemsIndexed(
                    items = ProfileItem.userInfo,
                    key = { _, item -> item.name }) { index, item ->
                    if (item == ProfileItem.userName) profileNameView(item)
                    else buttonItemView(
                        item = item,
                        index = index,
                        count = ProfileItem.userInfo.size
                    )
                }

                item { Spacer(modifier = Modifier.size(24.dp)) }
            }

            AnimatedVisibility(
                visible = !isEditable,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(Dp.zero),
                ) {
                    itemsIndexed(
                        items = ProfileItem.buttons,
                        key = { _, item -> item.name }) { index, item ->
                        buttonItemView(item = item, index = index, count = ProfileItem.buttons.size)
                    }
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        body()
        TopBar(navController = navController)
    }

    if (showHelpSheet) {
        HelpView(sheetState = sheetState) {
            showHelpSheet = false
        }
    }

    if (!isLoggedIn && showLogInSheet) {
        OnboardingSheetView(sheetState = sheetState) { showHelpSheet = false }
    }
}