package com.example.getirlite.model.extension

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.getirlite.view.Screen

inline val Dp.Companion.zero: Dp get() = Dp(0f)
inline val Dp.Companion.padding: Dp get() = 15.dp
inline val Dp.Companion.margin: Dp get() = 20.dp

inline val Dp.margin: Dp get() = this + Dp.margin
inline val Dp.padding: Dp get() = this + Dp.padding
inline val Dp.handle: Dp get() = this + Screen.Dimensions.sheetHandle

inline val Dp.double: Dp get() = times(2)
inline val Dp.triple: Dp get() = times(3)
inline val Dp.quadruple: Dp get() = times(4)

inline val Dp.half: Dp get() = div(2)
inline val Dp.third: Dp get() = div(3)
inline val Dp.quarter: Dp get() = div(4)