package com.example.getirlite.view.components.bars

import android.icu.text.DecimalFormat
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBackIos
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.view.Controllers
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.model.extension.padding
import com.example.getirlite.view.fragments.cart.CartViewModel

@Composable
fun TopBar(navController: NavController) {
    val cartViewModel: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalAmount by cartViewModel.totalAmount.collectAsState()

    val controller = remember { Controllers.parse(navController.currentDestination?.route ?: "") }

    Box(
        contentAlignment = Alignment.Center
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .height(height = 70.dp)
                .background(color = Color.Blue)
        ) {
            Image(
                imageVector = Icons.AutoMirrored.Rounded.ArrowBackIos,
                contentDescription = "",
                colorFilter = ColorFilter.tint(color = Color.White),
                modifier = Modifier
                    .padding(start = Dp.padding)
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            navController.popBackStack()
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )

            )

            Spacer(modifier = Modifier.weight(1f))

            if (cartItems.isNotEmpty())
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(end = Dp.padding)
                        .width(width = 90.dp)
                        .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                        .clickable(
                            onClick = {
                                navController.navigate(NavigationItem.Cart.route)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_cart),
                        contentDescription = "",
                        modifier = Modifier
                            .size(34.dp)
                    )

                    SimpleText(
                        text = "₺${DecimalFormat("0.00").format(totalAmount)}",
                        color = Color.Blue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        maxLines = 1
                    )
                }

        }

        SimpleText(
            text = controller.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}