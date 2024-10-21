package com.example.getirlite.view.components.suggestions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.getirlite.R
import com.example.getirlite.view.Screen
import com.example.getirlite.view.components.widgets.SimpleText
import com.example.getirlite.view.fragments.productList.components.ProductItemView
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.padding
import com.example.getirlite.model.extension.quadruple
import com.example.getirlite.model.product.Product

@Composable
fun SuggestedProductsView(navController: NavController, suggestedProducts: List<Product>) {
    SimpleText(
        text = stringResource(R.string.suggestedItems),
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .padding(horizontal = Dp.padding, vertical = 8.dp)
            .fillMaxWidth()
    )

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(Dp.margin),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        items(suggestedProducts, key = { product -> product.id }) {
            ProductItemView(navController = navController, product = it, itemSize = (Screen.width - Dp.margin.quadruple) / 3)
        }
    }
}