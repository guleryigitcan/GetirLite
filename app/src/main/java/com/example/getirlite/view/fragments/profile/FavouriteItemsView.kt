package com.example.getirlite.view.fragments.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.productList.components.ProductItemView
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.quadruple
import com.example.getirlite.view.fragments.cart.CartViewModel

@Composable
fun FavouriteItemsView(navController: NavController) {
    val cartViewModel: CartViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)
    val favProducts by cartViewModel.favouriteProducts.collectAsStateWithLifecycle()

    @Composable
    fun body() {
        if (favProducts.isEmpty())
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Spacer(modifier = Modifier.weight(1f))

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier
                        .padding(horizontal = Dp.padding)
                        .background(
                            color = Color.Transparent,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Blue,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(Dp.padding)
//                        .clip(RoundedCornerShape(8.dp))
                ) {
                    SimpleText(
                        text = stringResource(R.string.noFavouriteItemsTitle),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )

                    SimpleText(
                        text = stringResource(R.string.noFavouriteItemsBody),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
            }
        else
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                verticalArrangement = Arrangement.spacedBy(Dp.margin)
            ) {
                items(favProducts, key = { product -> product.id }) {
                    ProductItemView(
                        navController = navController,
                        product = it,
                        itemSize = (Screen.width - Dp.margin.quadruple) / 3
                    )
                }
            }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        TopBar(navController = navController)
        body()
    }
}