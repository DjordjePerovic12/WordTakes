package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bokadev.word_takes.core.utils.observeWithLifecycle
import com.bokadev.word_takes.presentation.stats.StatsEvent
import com.bokadev.word_takes.presentation.stats.StatsViewModel
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen(
    showSnackBar: (String) -> Unit,
    viewModel: StatsViewModel = koinViewModel(),

    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()


    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }


    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()


    if (state.shouldShowRatingsBottomSheet) {
        WordAllRatingsBottomSheet(
            sheetState = sheetState,
            word = state.selectedWord,
            ratingsItems = state.ratingsItems,
            canLoadMoreRatings = state.canLoadMoreRatings,
            isRatingsLoadingNextPage = state.isRatingsLoadingNextPage,
            onDismiss = {
                viewModel.onEvent(
                    StatsEvent
                        .DismissBottomSheet
                )
            },
            onLoadNext = {
                viewModel.onEvent(
                    StatsEvent
                        .LoadRatingsNextPage
                )
            }

        )
    }

    if (state.items.isEmpty()) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .background(WordTakesTheme.colors.backgroundPrimary)
                .padding(horizontal = 20.dp)
        ) {
            Text(
                text = "No stats available for this month",
                color = WordTakesTheme.colors.wordTakesWhite,
                style = WordTakesTheme.typogrpahy.geistSemiBold32,
                textAlign = TextAlign.Center
            )
        }

    } else
        LazyColumn(
            modifier = Modifier.fillMaxSize()
                .background(WordTakesTheme.colors.backgroundPrimary)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {
                Spacer(modifier = Modifier.height(50.dp))
            }

            val best = state.items.firstOrNull()

            if (best != null) {
                item {
                    Text(
                        text = "Best word of the month",
                        color = WordTakesTheme.colors.wordTakesWhite,
                        style = WordTakesTheme.typogrpahy.geistSemiBold32
                    )
                }

                item {

                    WordCard(
                        wordItem = best,
                        onRateClick = {
                            viewModel.onEvent(
                                StatsEvent
                                    .OnRateWordClick(
                                        wordId = best.id,
                                        reaction = it
                                    )
                            )
                        },
                        onUserNameClick = {

                        },
                        onSeeRatingsClick = { wordId, word ->
                            viewModel.onEvent(
                                StatsEvent
                                    .OpenBottomSheet(
                                        wordId = wordId,
                                        selectedWord = word
                                    )
                            )
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

            val worst = state.items.getOrNull(1)

            if (worst != null) {
                item {
                    Text(
                        text = "Worst word of the month",
                        color = WordTakesTheme.colors.wordTakesWhite,
                        style = WordTakesTheme.typogrpahy.geistSemiBold32
                    )
                }

                item {

                    WordCard(
                        wordItem = worst,
                        onRateClick = {
                            viewModel.onEvent(
                                StatsEvent
                                    .OnRateWordClick(
                                        wordId = worst.id,
                                        reaction = it
                                    )
                            )
                        },
                        onUserNameClick = {

                        },
                        onSeeRatingsClick = { wordId, word ->
                            viewModel.onEvent(
                                StatsEvent
                                    .OpenBottomSheet(
                                        wordId = wordId,
                                        selectedWord = word
                                    )
                            )
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }


            val controversial = state.items.getOrNull(2)

            if (controversial != null) {
                item {
                    Text(
                        text = "Most controversial word of the month",
                        color = WordTakesTheme.colors.wordTakesWhite,
                        style = WordTakesTheme.typogrpahy.geistSemiBold32
                    )
                }

                item {
                    WordCard(
                        wordItem = controversial,
                        onRateClick = {
                            viewModel.onEvent(
                                StatsEvent
                                    .OnRateWordClick(
                                        wordId = controversial.id,
                                        reaction = it
                                    )
                            )
                        },
                        onUserNameClick = {

                        },
                        onSeeRatingsClick = { wordId, word ->
                            viewModel.onEvent(
                                StatsEvent
                                    .OpenBottomSheet(
                                        wordId = wordId,
                                        selectedWord = word
                                    )
                            )
                        }
                    )
                }
            }

        }

}
