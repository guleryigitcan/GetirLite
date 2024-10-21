package com.example.getirlite.view.fragments.productDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
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
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.components.suggestions.SuggestedProductsView
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.view.fragments.cart.CartViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ProductDetailView(navController: NavController, productId: String) {
    val model: ProductListViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val cartViewModel: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)

    val suggestedProducts by model.suggestedProducts.collectAsStateWithLifecycle()
    val counts by cartViewModel.productCounts.collectAsState()
    val count = counts[productId] ?: 0

    val favouriteItems by cartViewModel.favouriteProducts.collectAsStateWithLifecycle()
    val isFavourite = favouriteItems.any { it.id == productId }

    val product = model.findProductById(productId) ?: return

    @Composable
    fun StickyAction() {
        if (count > 0)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(bottom = 70.dp)
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
                ) {
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

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
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

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(50.dp)
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
                }
            }

        if (count == 0)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .padding(horizontal = Dp.margin)
                    .fillMaxWidth()
                    .height(height = 50.dp)
                    .background(
                        color = Color.Blue,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clickable(
                        onClick = {
                            cartViewModel.addToCart(product = product)
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            ) {
                SimpleText(
                    text = stringResource(R.string.addToCart),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }


    }

    @Composable
    fun body() {
        Box(
            contentAlignment = Alignment.TopEnd
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(top = Dp.padding)
                    .fillMaxSize()
            ) {
                GlideImage(
                    model = product.imageURL,
                    contentDescription = ""
                )

                Spacer(modifier = Modifier.size(Dp.margin))

                SimpleText(
                    text = product.priceText,
                    color = Color.Blue,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )

                SimpleText(
                    text = product.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )

                SimpleText(
                    text = product.attribute ?: "",
                    color = Color.DarkGray,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.size(Dp.margin))

                SuggestedProductsView(navController = navController, suggestedProducts = suggestedProducts)

                Spacer(modifier = Modifier.weight(1f))

                StickyAction()
            }

            Image(
                imageVector = if (isFavourite) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
                colorFilter = ColorFilter.tint(Color.Blue),
                contentDescription = "",
                modifier = Modifier
                    .padding(Dp.padding)
                    .size(32.dp)
                    .clickable(
                        onClick = {
                            if (isFavourite) cartViewModel.removeFavoriteProduct(product = product)
                            else cartViewModel.addFavouriteProduct(product = product)
                        },
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    )
            )
        }
    }

    Column {
        TopBar(navController = navController)
        body()
    }
}