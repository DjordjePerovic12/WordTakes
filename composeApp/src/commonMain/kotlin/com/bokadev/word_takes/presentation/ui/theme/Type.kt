package com.bokadev.word_takes.presentation.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import word_takes.composeapp.generated.resources.Res
import word_takes.composeapp.generated.resources.geist_black
import word_takes.composeapp.generated.resources.geist_bold
import word_takes.composeapp.generated.resources.geist_light
import word_takes.composeapp.generated.resources.geist_medium
import word_takes.composeapp.generated.resources.geist_regular
import word_takes.composeapp.generated.resources.geist_semiBold


@Immutable
data class WordTakesTypography(
    val geistBold32: TextStyle,
    val geistRegular12: TextStyle,
    val geistRegular13: TextStyle,
    val geistRegular14: TextStyle,
    val geistRegular15: TextStyle,
    val geistRegular16: TextStyle,
    val geistRegular18: TextStyle,
    val geistRegular24: TextStyle,
    val geistSemiBold12: TextStyle,
    val geistSemiBold14: TextStyle,
    val geistSemiBold16: TextStyle,
    val geistSemiBold24: TextStyle,
    val geistSemiBold32: TextStyle,
    val geistMedium24: TextStyle,
    val geistMedium18: TextStyle,
    val geistMedium13: TextStyle,
    val geistMedium15: TextStyle,
    val geistMedium16: TextStyle,
    val geistBold13: TextStyle,
    val geistBold14: TextStyle,
    val geistBold16: TextStyle,
    val geistBold18: TextStyle,
    val geistBold24: TextStyle,
    val geistBlack12: TextStyle,
    val geistBlack16: TextStyle,
    val geistLight14: TextStyle,
    val geistLight18: TextStyle,
    val geistBlack14: TextStyle,
    val geistBlack24: TextStyle,
)

val LocalWordTakesTypography = staticCompositionLocalOf<WordTakesTypography> {
    error("CerovoTypography not provided")
}

// Create this as a @Composable function
@Composable
fun rememberWordTakesTypography(): WordTakesTypography {
    // Load fonts inside @Composable context
    val geistFontFamily = FontFamily(
        Font(Res.font.geist_light, FontWeight.Light),
        Font(Res.font.geist_regular, FontWeight.Normal),
        Font(Res.font.geist_medium, FontWeight.Medium),
        Font(Res.font.geist_semiBold, FontWeight.SemiBold),
        Font(Res.font.geist_bold, FontWeight.Bold),
        Font(Res.font.geist_black, FontWeight.Black),
    )

    return WordTakesTypography(
        geistBold32 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        ),
        geistRegular12 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
        ),
        geistRegular13 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
        ),
        geistRegular14 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
        ),
        geistRegular15 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
        ),
        geistRegular16 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
        ),
        geistRegular18 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 18.sp,
        ),
        geistRegular24 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
        ),
        geistSemiBold12 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
        ),
        geistSemiBold14 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 14.sp,
        ),
        geistSemiBold16 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
        ),
        geistSemiBold24 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 24.sp,
        ),
        geistSemiBold32 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.SemiBold,
            fontSize = 32.sp,
        ),
        geistMedium24 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
        ),
        geistMedium18 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
        ),
        geistMedium13 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
        ),
        geistMedium15 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
        ),
        geistMedium16 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
        ),
        geistBold13 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
        ),
        geistBold14 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
        ),
        geistBold16 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
        ),
        geistBold18 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        ),
        geistBold24 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        ),
        geistBlack12 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 12.sp,
        ),
        geistBlack16 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 16.sp,
        ),
        geistLight14 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
        ),
        geistLight18 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Light,
            fontSize = 18.sp,
        ),
        geistBlack14 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 14.sp,
        ),
        geistBlack24 = TextStyle(
            fontFamily = geistFontFamily,
            fontWeight = FontWeight.Black,
            fontSize = 24.sp,
        ),
    )
}
