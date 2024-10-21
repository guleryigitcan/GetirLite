package com.example.getirlite.view.fragments.cart

import android.icu.text.DecimalFormat
import android.view.Gravity
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.zero
import com.example.getirlite.model.product.Product
import com.example.getirlite.ui.theme.Divider
import com.example.getirlite.view.NavigationItem
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.components.suggestions.SuggestedProductsView
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.productList.ProductListViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CartView(navController: NavController) {
    val model: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val productListViewModel: ProductListViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val counts by model.productCounts.collectAsState()

    val cartItems by model.cartItems.collectAsStateWithLifecycle()
    val totalAmount by model.totalAmount.collectAsState()
    val suggestedProducts by productListViewModel.suggestedProducts.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(cartItems) {
        if (cartItems.isEmpty()) navController.navigate(NavigationItem.ProductList.route)
    }

    @Composable
    fun StickyAction(product: Product) {
        val count = counts[product.id] ?: 0

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Image(
                imageVector = if (count == 1) Icons.Rounded.Delete else Icons.Rounded.Remove,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Blue),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            model.removeToCart(product = product)
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )

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
                imageVector = Icons.Rounded.Add,
                contentDescription = "",
                colorFilter = ColorFilter.tint(Color.Blue),
                modifier = Modifier
                    .size(24.dp)
                    .clickable(
                        onClick = {
                            model.addToCart(product = product)
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )
        }
    }

    @Composable
    fun LazyItemScope.itemView(product: Product) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(Dp.zero),
            modifier = Modifier
                .padding(top = 8.dp)
                .animateItem(placementSpec = tween(durationMillis = 500))
                .clickable(
                    onClick = {
                        navController.navigate(NavigationItem.ProductDetail.createRoute(productId = product.id))
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                GlideImage(
                    model = product.imageURL ?: product.thumbnailURL ?: product.squareThumbnailURL,
                    contentDescription = "",
                    modifier = Modifier
                        .size(74.dp)
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

                Spacer(modifier = Modifier.size(Dp.padding))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.weight(1f)
                ) {
                    SimpleText(
                        text = product.name,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    SimpleText(
                        text = product.attribute ?: "",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.DarkGray
                    )

                    SimpleText(
                        text = product.priceText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue
                    )

                }

                Spacer(modifier = Modifier.weight(1f))

                StickyAction(product = product)
            }

            HorizontalDivider(color = Color.Divider, thickness = 2.dp)
        }
    }

    @Composable
    fun button() {
        val animatedTotalAmount by animateFloatAsState(
            targetValue = totalAmount.toFloat(),
            label = "",
            animationSpec = tween(durationMillis = 300)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(bottom = 70.dp)
                .padding(horizontal = Dp.margin)
                .fillMaxWidth()
                .height(height = 50.dp)
                .clickable(
                    onClick = {
                        val message = "Siparişiniz Aldık! Toplam Ücret: ₺${"%.2f".format(totalAmount)}"
                        val toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
                        toast.setGravity(Gravity.CENTER, 0,0)
                        toast.show()

                        model.clearCart()
                        navController.navigate(NavigationItem.ProductList.route)
                    },
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                )
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(weight = 0.7f)
                    .fillMaxHeight()
                    .background(
                        color = Color.Blue,
                        shape = RoundedCornerShape(topStart = 8.dp, bottomStart = 8.dp)
                    )
            ) {
                SimpleText(
                    text = stringResource(R.string.completeCart),
                    color = Color.White,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(weight = 0.3f)
                    .fillMaxHeight()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(topEnd = 8.dp, bottomEnd = 8.dp)
                    )
            ) {
                SimpleText(
                    text = "₺${DecimalFormat("0.00").format(animatedTotalAmount)}",
                    fontSize = 20.sp,
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    @Composable
    fun body() {
        Column {
            Spacer(modifier = Modifier.size(Screen.Dimensions.topBar))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Dp.zero),
                contentPadding = PaddingValues(horizontal = Dp.margin),
            ) {
                items(
                    items = cartItems,
                    key = { product -> product.id }
                ) {
                   itemView(product = it)
                }
            }

            SuggestedProductsView(navController = navController, suggestedProducts = suggestedProducts)

            Spacer(modifier = Modifier.weight(1f))

            button()
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        body()
        TopBar(navController = navController)
    }
}