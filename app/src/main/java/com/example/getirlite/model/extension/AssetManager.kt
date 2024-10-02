package com.example.getirlite.model.extension

import android.content.Context
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.example.getirlite.MainActivity
import com.example.getirlite.R

object AssetManager {
    internal fun drawable(@DrawableRes id: Int): Drawable? = drawable(MainActivity.instance, id)
    internal fun drawable(context: Context, id: Int): Drawable? = ContextCompat.getDrawable(context, id)
    internal fun drawable(id: String): Drawable? = drawable(MainActivity.instance, id.drawableRes)

    internal fun color(@ColorRes id: Int): Int = color(MainActivity.instance, id)
    internal fun color(context: Context, @ColorRes id: Int): Int = ContextCompat.getColor(context, id)
    internal fun color(id: String): Int = color(id.colorRes)
    internal fun color(context: Context, id: String): Int = color(context, id.colorRes)

    internal fun string(@StringRes id: Int): String = string(MainActivity.instance, id)
    internal fun string(context: Context, @StringRes id: Int): String = context.getString(id)
    internal fun string(id: String): String = string(id.stringRes)
    internal fun string(context: Context, id: String): String = string(context, id.stringRes)

    internal fun font(@FontRes font: Int): Typeface? = font(MainActivity.instance, font)
    internal fun font(context: Context, @FontRes font: Int): Typeface? = ResourcesCompat.getFont(context, font)

    internal fun dimen(@DimenRes id: Int): Int = dimen(MainActivity.instance, id)
    internal fun dimen(context: Context, @DimenRes id: Int): Int = context.resources.getDimensionPixelSize(id)
}

fun getResId(resName: String, c: Class<*>): Int {
    try {
        val idField = c.getDeclaredField(resName)
        return idField.getInt(idField)
    } catch (e: Exception) {
        e.printStackTrace()
        return -1
    }
}


inline val String.stringRes: Int get() = getResId(this, R.string::class.java)
inline val String.drawableRes: Int get() = getResId(this, R.drawable::class.java)
inline val String.colorRes: Int get() = getResId(this, R.color::class.java)
inline val String.dimenRes: Int get() = getResId(this, R.dimen::class.java)
//inline val String.rawRes: Int get() = getResId(this, R.raw::class.java)
inline val Int.string: String get() = string(MainActivity.instance)
inline val Int.drawable: Drawable? get() = drawable(MainActivity.instance)
inline val Int.color: Int get() = color(MainActivity.instance)
fun Int.string(context: Context): String = try {
    AssetManager.string(context, this)
} catch (ignored: Exception) {
    ""
}

fun Int.drawable(context: Context): Drawable? = try {
    AssetManager.drawable(context, this)
} catch (ignored: Exception) {
    null
}

fun Int.color(context: Context): Int = try {
    AssetManager.color(context, this)
} catch (ignored: Exception) {
    0
}