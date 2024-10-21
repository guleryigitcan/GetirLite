package com.example.getirlite.view

import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.getirlite.MainActivity
import com.example.getirlite.model.extension.half
import com.example.getirlite.model.extension.margin
import com.example.getirlite.model.extension.quadruple
import com.example.getirlite.model.extension.third

object Screen {
    val width: Dp
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return pixelsToDp(MainActivity.instance.windowManager.currentWindowMetrics.bounds.width())
            val screenSize = Point()
            MainActivity.instance.windowManager.defaultDisplay.getRealSize(screenSize)
            return pixelsToDp(screenSize.x.toFloat())
        }

    val height: Dp
        get() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) return pixelsToDp(MainActivity.instance.windowManager.currentWindowMetrics.bounds.height())
            val screenSize = Point()
            MainActivity.instance.windowManager.defaultDisplay.getRealSize(screenSize)
            return pixelsToDp(screenSize.y.toFloat())
        }

    val size: Size get() = Size(width.value, height.value)

    val center: Offset get() = size.center

    val buttonSize: Dp get() = (width - Dp.margin.quadruple).third
    val titleOffsetY: Int = dpToPixels(40.dp)

    object Dimensions {
        val topBar: Dp get() = 70.dp
        val topBarWithSearch: Dp get() = topBar + searchBar + Dp.margin.half
        val topBarWithPicker: Dp get() = topBar + picker + Dp.margin.half
        val bottomBar: Dp get() = 80.0.dp
        val searchBar: Dp get() = 38.0.dp
        val picker: Dp get() = 40.0.dp
        val sheetHandle: Dp get() = 15.0.dp
    }

    fun pixelsToDp(px: Float): Dp = (px / displayMultiplier).dp

    fun pixelsToDp(px: Int): Dp = pixelsToDp(px.toFloat())

    fun dpToPixels(dp: Float): Int = (dp * displayMultiplier).toInt()

    fun dpToPixels(dp: Dp): Int = dpToPixels(dp.value)

    private val displayMultiplier: Float get() = (Resources.getSystem().displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}

inline val Dp.pixels: Int get() = Screen.dpToPixels(this)
inline val Dp.floatPixels: Float get() = Screen.dpToPixels(this).toFloat()
inline val Dp.doublePixels: Double get() = Screen.dpToPixels(this).toDouble()