package com.example.getirlite

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.example.getirlite.ui.theme.GetirLiteTheme
import com.example.getirlite.view.AppNavHost
import com.example.getirlite.view.components.bars.BottomBar
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.onboarding.AccountManager
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val productListViewModel: ProductListViewModel by viewModels()
    val cartViewModel: CartViewModel by viewModels()
    val accountManager: AccountManager by viewModels()

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContent {
            val navController = rememberNavController()

            GetirLiteTheme {
                Box(
                    contentAlignment = Alignment.TopCenter
                ) {
                    Column {
                        AppNavHost(
                            modifier = Modifier.weight(1f),
                            navController = navController
                        )
                        BottomBar(navController = navController)
                    }
                }
            }
        }
    }

    var googleResult: ActivityResultLauncher<Intent> = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            accountManager.handleSignInResult(task)
        }
    }

    companion object {
        lateinit var instance: MainActivity
    }
}
