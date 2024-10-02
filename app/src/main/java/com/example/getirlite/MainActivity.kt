package com.example.getirlite

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.commit
import androidx.navigation.fragment.NavHostFragment
import com.example.getirlite.databinding.ActivityMainBinding
import com.example.getirlite.view.fragments.onboarding.AccountManager
import com.example.getirlite.model.User
import com.example.getirlite.view.fragments.cart.CartViewModel
import com.example.getirlite.view.fragments.productList.ProductListViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private val productViewModel: ProductListViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val accountManager: AccountManager by viewModels()

    init {
        instance = this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment

        val navController = navHostFragment.navController
        val navGraph = navController.navInflater.inflate(R.navigation.nav_graph)

        if (User.didSeeOnboarding.bool) navGraph.setStartDestination(R.id.productListFragment)
         else navGraph.setStartDestination(R.id.onboardingFragment)

        navController.graph = navGraph

        accountManager.start()

        binding.topBar.set(navController, cartViewModel)
        binding.bottomBar.set(navController)
    }

    var googleResult: ActivityResultLauncher<Intent> = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                accountManager.handleSignInResult(task)
            }
        }

    companion object {
        lateinit var instance: MainActivity
    }
}