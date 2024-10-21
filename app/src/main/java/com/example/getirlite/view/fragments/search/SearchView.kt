package com.example.getirlite.view.fragments.search

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.getirlite.MainActivity
import com.example.getirlite.R
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.bars.SearchBar
import com.example.getirlite.view.components.bars.SearchBehaviour
import com.example.getirlite.view.components.bars.SearchViewModel
import com.example.getirlite.view.components.bars.TopBar
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.example.getirlite.view.fragments.productList.components.ProductItemView
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.quadruple
import com.example.getirlite.model.product.Product

@Composable
fun SearchView(navController: NavController) {
    val model: ProductListViewModel = hiltViewModel(viewModelStoreOwner = MainActivity.instance)

    val verticalProducts by model.verticalProducts.collectAsStateWithLifecycle()
    val suggestedProducts by model.suggestedProducts.collectAsStateWithLifecycle()

    val allProducts by remember { derivedStateOf { verticalProducts + suggestedProducts } }

    val shuffledKeywords = remember { PopularSearchKeywords.entries.shuffled().take(10) }

    val search = viewModel<SearchViewModel<Product>>(
        factory = SearchViewModel.Factory(
            items = allProducts,
            limit = 20,
            searchBehavior = SearchBehaviour.empty
        )
    )
    val filteredItems by search.filteredItems.collectAsState()


    @Composable
    fun contentView() {
        SimpleText(
            text = stringResource(R.string.popular_search),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .padding(start = 8.dp)
                .padding(vertical = 4.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(Dp.margin),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 4.dp)
                .background(color = Color.White)
                .padding(vertical = 8.dp)
        ) {
            items(
                shuffledKeywords,
                key = { item -> item.name }) {
                SimpleText(
                    text = it.name,
                    fontSize = 14.sp,
                    color = Color.Blue,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(vertical = 8.dp, horizontal = Dp.padding)
                        .clickable(
                            onClick = {
                                search.search(it.name)
                            },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        )
                )
            }
        }
    }

    @Composable
    fun body() {
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Spacer(modifier = Modifier.size(Screen.Dimensions.topBar))

            SearchBar(model = search)

            if (filteredItems.isNotEmpty())
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    verticalArrangement = Arrangement.spacedBy(Dp.margin)
                ) {
                    items(filteredItems, key = { product -> product.id }) {
                        ProductItemView(
                            navController = navController,
                            product = it,
                            itemSize = (Screen.width - Dp.margin.quadruple) / 3
                        )
                    }
                }
            else contentView()
        }
    }

    Box(
        contentAlignment = Alignment.TopCenter,
    ) {
        body()
        TopBar(navController = navController)
    }
}