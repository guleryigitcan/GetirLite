package com.example.getirlite.view.fragments.productList.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.getirlite.MainActivity
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.triple
import com.example.getirlite.model.product.Product
import com.example.getirlite.ui.theme.Divider
import com.example.getirlite.view.fragments.cart.CartViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductItemView(navController: NavController, product: Product, itemSize: Dp) {
    val cartViewModel: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val counts by cartViewModel.productCounts.collectAsState()
    val count = counts[product.id] ?: 0

    @Composable
    fun StickyAction() {
        Box(
            contentAlignment = Alignment.TopEnd,
            modifier = Modifier
                .padding(end = 8.dp, bottom = 12.dp)
                .background(color = Color.White, shape = RoundedCornerShape(12.dp))
                .padding(4.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Image(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(Color.Blue),
                    modifier = Modifier
                        .size(24.dp)
                        .clickable(
                            onClick = {
                                cartViewModel.addToCart(product = product)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                )

                if (count > 0) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(24.dp)
                            .background(color = Color.Blue, shape = RectangleShape)
                    ) {
                        SimpleText(
                            text = count.toString(),
                            textAlign = TextAlign.Center,
                            color = Color.White,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Image(
                        imageVector = if (count == 1) Icons.Rounded.Delete else Icons.Rounded.Remove,
                        contentDescription = "",
                        colorFilter = ColorFilter.tint(Color.Blue),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable(
                                onClick = {
                                    cartViewModel.removeToCart(product = product)
                                },
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            )
                    )
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.TopEnd,
        modifier = Modifier
            .clickable(
                onClick = {
                    navController.navigate(NavigationItem.ProductDetail.createRoute(product.id))
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            )
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier
                .padding(horizontal = Dp.margin)
                .size(width = itemSize, height = itemSize + Dp.margin.triple)
        ) {
            GlideImage(
                model = product.imageURL ?: product.thumbnailURL ?: product.squareThumbnailURL,
                contentDescription = "",
                modifier = Modifier
                    .background(
                        color = Color.Transparent,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 2.dp,
                        color = Color.Divider,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clip(RoundedCornerShape(16.dp))
            )

            SimpleText(
                text = product.priceText,
                color = Color.Blue,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )

            SimpleText(
                text = product.name,
                color = Color.Black,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            SimpleText(
                text = product.attribute ?: "",
                color = Color.DarkGray,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp
            )
        }

        StickyAction()
    }
}