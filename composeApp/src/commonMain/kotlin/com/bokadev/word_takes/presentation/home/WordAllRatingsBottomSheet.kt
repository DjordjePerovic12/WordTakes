package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bokadev.word_takes.core.utils.noRippleClickable
import com.bokadev.word_takes.data.remote.dto.ReactionsEnum
import kotlinx.coroutines.flow.distinctUntilChanged
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.jetbrains.compose.resources.vectorResource
import word_takes.composeapp.generated.resources.Res
import word_takes.composeapp.generated.resources.icon_eye_off

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WordAllRatingsBottomSheet(
    sheetState: SheetState,
    state: HomeState,
    word: String,
    onDismiss: () -> Unit,
    onLoadNext: () -> Unit
) {

    val listState = rememberLazyListState()

    // ✅ stable function + latest state for collectors
    val latestLoadNext by rememberUpdatedState(newValue = onLoadNext)
    val latestState by rememberUpdatedState(newValue = state)

    var userHasScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { inProgress -> if (inProgress) userHasScrolled = true }
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1 }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex < 0) return@collect
                if (!userHasScrolled) return@collect

                val total = listState.layoutInfo.totalItemsCount
                val threshold = 3
                val nearEnd = lastVisibleIndex >= total - 1 - threshold

                if (nearEnd && latestState.canLoadMoreRatings && !latestState.isRatingsLoadingNextPage) {
                    latestLoadNext()
                }
            }
    }

    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = onDismiss,
        containerColor = WordTakesTheme.colors.backgroundPrimary,
        dragHandle = null,
        shape = RoundedCornerShape(
            topStart = 16.dp, topEnd = 16.dp
        ),
        contentColor = WordTakesTheme.colors.colorWhite,
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(15.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.background(WordTakesTheme.colors.backgroundPrimary)
                .padding(horizontal = 20.dp)
        ) {

            Spacer(modifier = Modifier.height(10.dp))
            Icon(
                imageVector = vectorResource(Res.drawable.icon_eye_off),
                contentDescription = null,
                modifier = Modifier.align(Alignment.End)
                    .noRippleClickable {
                        onDismiss()
                    },
            )
            Text(
                text = word,
                color = WordTakesTheme.colors.wordTakesWhite,
                style = WordTakesTheme.typogrpahy.geistSemiBold24
            )

            state.ratingsItems.forEach {

                val decideColor = when (it.reaction) {
                    ReactionsEnum.GOOD.name.lowercase() -> WordTakesTheme.colors.wordTakesGood
                    ReactionsEnum.AMAZING.name.lowercase() -> WordTakesTheme.colors.wordTakesAmazing
                    ReactionsEnum.BAD.name.lowercase() -> WordTakesTheme.colors.wordTakesBad
                    ReactionsEnum.AWFUL.name.lowercase() -> WordTakesTheme.colors.wordTakesAwful
                    else -> WordTakesTheme.colors.colorWhite
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = it.user.name,
                        color = WordTakesTheme.colors.wordTakesWhite,
                        style = WordTakesTheme.typogrpahy.geistMedium18
                    )

                    Text(
                        text = it.reaction.uppercase(),
                        color = decideColor,
                        style = WordTakesTheme.typogrpahy.geistBold24
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                HorizontalDivider(modifier = Modifier.background(WordTakesTheme.colors.wordTakesWhite))

            }

        }
    }
}
