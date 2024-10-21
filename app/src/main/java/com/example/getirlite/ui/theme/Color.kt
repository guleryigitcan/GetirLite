package com.example.getirlite.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Color.Companion.SimpleRed get() = Color(0xFFFF375F)
val Color.Companion.SimpleBlue get() = Color(0xFF0A84FF)
val Color.Companion.SimpleGreen get() = Color(0xFF4FFD7A)

val Color.Companion.Mint: Color get() = SimpleGreen
val Color.Companion.Brown: Color get() = Color(0xFF503417)
val Color.Companion.Purple: Color get() = Color(0xFF383880)
val Color.Companion.Orange: Color get() = Color(0xFFFE7000)
val Color.Companion.Peach: Color get() = Color(0xFFFF928B)
val Color.Companion.Gold: Color get() = Color(0xFFFFCC66)

val Color.Companion.BackgroundGradientStart get() = Color(0xFF121029)
val Color.Companion.BackgroundGradientEnd get() = Color(0xFF242052)
val Color.Companion.ButtonGradientStart get() = Color(0xFF5C3EEE)
val Color.Companion.ButtonGradientEnd get() = Color(0xFF7E3EED)

val Color.Companion.EmotionalActiveColor get() = SimpleRed
val Color.Companion.EmotionalPassiveColor get() = SimpleRed.opacity(0.3f)
val Color.Companion.PhysicalActiveColor get() = SimpleBlue
val Color.Companion.PhysicalPassiveColor get() = SimpleBlue.opacity(0.3f)
val Color.Companion.IntellectualActiveColor get() = SimpleGreen
val Color.Companion.IntellectualPassiveColor get() = SimpleGreen.opacity(0.3f)


val Color.Companion.Game1: Color get() = Color(0xFFC3AD5C)
val Color.Companion.Game2: Color get() = Color(0xFFBB4F4E)
val Color.Companion.Game3: Color get() = Color(0xFF62854B)
val Color.Companion.Game4: Color get() = Color(0xFF598B9E)
val Color.Companion.Game5: Color get() = Color(0xFF636191)

val Color.Companion.FarmBackground get() = Green.extraExtraDark

val Color.Companion.Box get() = White.opacity(0.05f)
val Color.Companion.Divider get() = White.opacity(0.15f)
val Color.Companion.TopBar get() = BackgroundGradientStart.interpolate(White, fraction = 0.1f).opacity(0.98f)

fun Color.opacity(alpha: Float) = copy(alpha = alpha)

val Color.transparent: Color get() = opacity(0.8f)
val Color.extraTransparent: Color get() = opacity(0.6f)
val Color.extraExtraTransparent: Color get() = opacity(0.4f)

val Color.extraExtraLight: Color get() = interpolate(to = Color.White, fraction = 0.6f)
val Color.extraLight: Color get() = interpolate(to = Color.White, fraction = 0.4f)
val Color.light: Color get() = interpolate(to = Color.White, fraction = 0.2f)
val Color.dark: Color get() = interpolate(to = Color.Black, fraction = 0.2f)
val Color.extraDark: Color get() = interpolate(to = Color.Black, fraction = 0.4f)
val Color.extraExtraDark: Color get() = interpolate(to = Color.Black, fraction = 0.6f)

fun Color.interpolate(to: Color, fraction: Float): Color = Color(red = (to.red - red) * fraction + red, green = (to.green - green) * fraction + green, blue = (to.blue - blue) * fraction + blue, alpha = (to.alpha - alpha) * fraction + alpha)