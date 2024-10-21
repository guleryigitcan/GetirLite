package com.example.getirlite.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.getirlite.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val fonts = FontFamily(
    Font(
        resId = R.font.font_regular,
        weight = FontWeight.Normal
    ),

    Font(
        resId = R.font.font_semibold,
        weight = FontWeight.SemiBold
    ),
    Font(
        resId = R.font.font_bold,
        weight = FontWeight.Bold
    )
)