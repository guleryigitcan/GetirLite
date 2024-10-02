package com.example.getirlite.view.components.bars

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.NavController
import com.example.getirlite.R
import com.example.getirlite.databinding.ComponentBottomBarBinding

class BottomBar(context: Context, attrs: AttributeSet?) : ConstraintLayout(context, attrs)  {
    private val binding = ComponentBottomBarBinding.inflate(LayoutInflater.from(context), this, true)
    private lateinit var navController: NavController

    fun set(navController: NavController) {
        this.navController = navController
        bindView()
    }

    private fun bindView() {
        binding.home.setOnClickListener {
            if (navController.currentDestination?.id != R.id.productListFragment)
                navController.navigate(R.id.action_global_productListFragment)
        }

        binding.search.setOnClickListener {
            if (navController.currentDestination?.id != R.id.searchFragment)
                navController.navigate(R.id.action_global_searchFragment)
        }

        binding.profile.setOnClickListener {
            if (navController.currentDestination?.id != R.id.profileFragment)
                navController.navigate(R.id.action_global_profileFragment)
        }
        navController.addOnDestinationChangedListener { _, destination, _ ->
          if (hasBottomBar(destination.id)) show() else hide()
        }
    }

    private fun show() {
        this.animate()
            .translationY(0f)
            .setDuration(300)
            .alpha(1f)
            .start()
    }

    private fun hide() {
        this.animate()
            .translationY(this.height.toFloat())
            .setDuration(300)
            .alpha(0f)
            .start()
    }

    private fun hasBottomBar(id: Int): Boolean = listOf(R.id.cartFragment, R.id.searchFragment, R.id.productListFragment, R.id.profileFragment).contains(id)

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        binding.home.setOnClickListener(null)
        binding.search.setOnClickListener(null)
        binding.profile.setOnClickListener(null)
    }

}