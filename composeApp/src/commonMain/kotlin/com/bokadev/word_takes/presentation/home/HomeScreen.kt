package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bokadev.word_takes.core.utils.observeWithLifecycle
import kotlinx.coroutines.flow.distinctUntilChanged
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    showSnackBar: (String) -> Unit,
    viewModel: HomeViewModel = koinViewModel(),

    ) {

    val state by viewModel.state.collectAsStateWithLifecycle()


    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }

    val listState = rememberLazyListState()

    // ✅ Keep a stable, up-to-date reference to the function (do NOT call it here)
    val latestLoadNextPage by rememberUpdatedState(newValue = { viewModel.executeGetWordsNextPage() })

    var userHasScrolled by remember { mutableStateOf(false) }

    LaunchedEffect(listState) {
        snapshotFlow { listState.isScrollInProgress }
            .distinctUntilChanged()
            .collect { inProgress ->
                if (inProgress) userHasScrolled = true
            }
    }

    val latestState by rememberUpdatedState(newValue = state)


    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()


    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1 }
            .distinctUntilChanged()
            .collect { lastVisibleIndex ->
                if (lastVisibleIndex < 0) return@collect
                if (!userHasScrolled) return@collect

                val total = listState.layoutInfo.totalItemsCount
                val threshold = 4
                val nearEnd = lastVisibleIndex >= total - 1 - threshold

                if (nearEnd && latestState.canLoadMore && !latestState.isLoadingNextPage) {
                    latestLoadNextPage()
                }
            }
    }


    if (state.shouldShowRatingsBottomSheet) {
        WordAllRatingsBottomSheet(
            sheetState = sheetState,
            word = state.selectedWord,
            state = state,
            onDismiss = {
                viewModel.onEvent(
                    HomeEvent.ToggleBottomSheet(
                        wordId = -1,
                        selectedWord = ""
                    )
                )
            },
            onLoadNext = {
                viewModel.onEvent(HomeEvent.LoadRatingsNextPage)
            }

        )
    }

    LazyColumn(
        state = listState,
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

        item {
            PostTakeItem(
                state = state,
                onWordChange = {
                    viewModel.onEvent(HomeEvent.OnWordChange(it))
                },
                onSubmitClick = {
                    viewModel.onEvent(HomeEvent.OnSubmitClick)
                }
            )
        }

        items(state.items) { word ->
            WordCard(
                wordItem = word,
                onRateClick = {
                    viewModel.onEvent(HomeEvent.OnRateWordClick(wordId = word.id, reaction = it))
                },
                onSeeRatingsClick = { wordId, word ->
                    viewModel.onEvent(
                        HomeEvent.ToggleBottomSheet(
                            wordId = wordId,
                            selectedWord =  word
                        )
                    )
                }
            )
        }
    }
}