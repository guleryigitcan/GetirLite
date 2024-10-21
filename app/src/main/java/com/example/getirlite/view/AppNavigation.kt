package com.example.getirlite.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.getirlite.view.fragments.cart.CartView
import com.example.getirlite.view.fragments.onboarding.OnboardingView
import com.example.getirlite.view.fragments.productDetail.ProductDetailView
import com.example.getirlite.view.fragments.productList.ProductListView
import com.example.getirlite.view.fragments.profile.FavouriteItemsView
import com.example.getirlite.view.fragments.profile.ProfileView
import com.example.getirlite.view.fragments.search.SearchView
import com.example.getirlite.model.User
import com.example.getirlite.model.extension.string
import com.example.getirlite.model.extension.stringRes

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = if (User.didSeeOnboarding.bool) NavigationItem.ProductList.route else NavigationItem.Onboarding.route,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(route = NavigationItem.Onboarding.route) {
            OnboardingView(navController = navController)
        }
        composable(route = NavigationItem.ProductList.route) {
            ProductListView(navController = navController)
        }
        composable(route = NavigationItem.ProductDetail.route) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")
            ProductDetailView(navController = navController, productId = productId ?: "")
        }
        composable(route = NavigationItem.Cart.route) {
            CartView(navController = navController)
        }
        composable(route = NavigationItem.Profile.route) {
            ProfileView(navController = navController)
        }
        composable(route = NavigationItem.FavouriteItems.route) {
            FavouriteItemsView(navController = navController)
        }
        composable(route = NavigationItem.Search.route) {
            SearchView(navController = navController)
        }

    }
}


enum class Controllers {
    onboarding,
    productList,
    productDetail,
    cart,
    search,
    profile,
    favouriteItems;

    val title: String get() = "${name}FragmentLabel".stringRes.string

    companion object {
        fun parse(raw: String): Controllers {
            if (raw.contains("/{productId}")) return productDetail
            return entries.first { it.name == raw }
        }
    }
}

sealed class NavigationItem(val route: String) {
    object Onboarding: NavigationItem(Controllers.onboarding.name)
    object ProductList : NavigationItem(Controllers.productList.name)
    object ProductDetail : NavigationItem("${Controllers.productDetail.name}/{productId}") {
        fun createRoute(productId: String) = "${Controllers.productDetail.name}/$productId"
    }
    object Cart : NavigationItem(Controllers.cart.name)
    object Profile: NavigationItem(Controllers.profile.name)
    object FavouriteItems: NavigationItem(Controllers.favouriteItems.name)
    object Search: NavigationItem(Controllers.search.name)
}