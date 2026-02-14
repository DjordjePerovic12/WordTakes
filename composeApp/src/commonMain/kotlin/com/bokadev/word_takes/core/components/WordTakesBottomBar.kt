package com.bokadev.word_takes.core.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bokadev.word_takes.core.utils.noRippleClickable
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.jetbrains.compose.resources.vectorResource
import word_takes.composeapp.generated.resources.Res
import word_takes.composeapp.generated.resources.ic_home
import word_takes.composeapp.generated.resources.ic_profile
import word_takes.composeapp.generated.resources.ic_stats

@Composable
fun WordTakesBottombAR(
    selected: Int,
    navigateToHome: () -> Unit,
    navigateToStats: () -> Unit,
    navigateToProfile: () -> Unit,
    modifier: Modifier = Modifier
) {


    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(WordTakesTheme.colors.wordTakesWhite.copy(.5f))
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(WordTakesTheme.colors.wordTakesDarkGrey.copy(.5f))
                .height(80.dp)
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp),
            color = Color.White,
            tonalElevation = 0.dp,          // IMPORTANT: disable tonal overlay
            shadowElevation = 12.dp,

            ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(WordTakesTheme.colors.wordTakesWhite.copy(.5f))
                    .padding(horizontal = 15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                BarIcon(
                    index = 0, selected = selected, onSelect = {
                        navigateToHome()
                    }
                )

                BarIcon(
                    index = 1, selected = selected, onSelect = {
                        navigateToStats()
                    }
                )

                BarIcon(
                    index = 2, selected = selected, onSelect = { navigateToProfile() }
                )
            }
        }
    }
}

@Composable
private fun BarIcon(
    index: Int,
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val isSelected = index == selected

    val decideIcon = when (index) {
        0 -> Res.drawable.ic_home
        1 -> Res.drawable.ic_stats
        2 -> Res.drawable.ic_profile
        else -> Res.drawable.ic_home
    }


    val decideTint =
        if (isSelected) WordTakesTheme.colors.wordTakesOrange else WordTakesTheme.colors.wordTakesDarkGrey


    Icon(
        imageVector = vectorResource(decideIcon),
        contentDescription = null,
        tint = decideTint,
        modifier = modifier
            .size(36.dp)
            .noRippleClickable {
                if (!isSelected || selected == 0 || selected == 1 || selected == 4)
                    onSelect(index)
            }
    )
}