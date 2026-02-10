package llc.amplitudo.cerovo.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import com.bokadev.word_takes.presentation.ui.theme.LocalWordTakesColors
import com.bokadev.word_takes.presentation.ui.theme.LocalWordTakesTypography
import com.bokadev.word_takes.presentation.ui.theme.WordTakesColors
import com.bokadev.word_takes.presentation.ui.theme.WordTakesTypography
import com.bokadev.word_takes.presentation.ui.theme.rememberWordTakesTypography
import com.bokadev.word_takes.presentation.ui.theme.wordTakesColors

@Composable
fun WordTakesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val typography = rememberWordTakesTypography()

    CompositionLocalProvider(
        LocalWordTakesColors provides wordTakesColors,  // Can use val directly
        LocalWordTakesTypography provides typography,    // Must call @Composable function
        content = content
    )
}
object WordTakesTheme {
    val colors: WordTakesColors
        @Composable get() = LocalWordTakesColors.current
    val typogrpahy: WordTakesTypography
        @Composable get() = LocalWordTakesTypography.current
}