package com.example.getirlite.view.fragments.productList

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.getirlite.MainActivity
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.fragments.productList.components.ProductItemView
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.quadruple

@Composable
fun ProductListView(navController: NavController) {
    val model: ProductListViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)

    val verticalProducts by model.verticalProducts.collectAsStateWithLifecycle()
    val suggestedProducts by model.suggestedProducts.collectAsStateWithLifecycle()
    val itemSize = remember { (Screen.width - Dp.margin.quadruple) / 3 }

    @Composable
    fun body() {
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(Dp.margin),
            modifier = Modifier
                .padding(top = Dp.padding)
                .fillMaxSize()

        ) {
            item { Spacer(modifier = Modifier.size(Screen.Dimensions.topBar)) }

            item {
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(Dp.margin),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(suggestedProducts, key = { product -> product.id }) {
                        ProductItemView(
                            navController = navController,
                            product = it,
                            itemSize = (Screen.width - Dp.margin.quadruple) / 3
                        )
                    }
                }
            }

            item {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(Dp.margin),
                    userScrollEnabled = false,
                    modifier = Modifier
                        .heightIn(max = (itemSize + Dp.margin.quadruple).times(verticalProducts.size.div(3) + 1))
                ) {
                    items(verticalProducts, key = { product -> product.id }) {
                        ProductItemView(
                            navController = navController,
                            product = it,
                            itemSize = itemSize
                        )
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
}