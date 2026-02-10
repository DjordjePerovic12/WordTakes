package com.bokadev.word_takes.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bokadev.word_takes.core.utils.observeWithLifecycle
import llc.amplitudo.cerovo.ui.theme.WordTakesTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(showSnackBar: (String) -> Unit, viewModel: HomeViewModel = koinViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()


    viewModel.snackBarChannel.observeWithLifecycle { message ->
        showSnackBar(message)
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize()
            .background(WordTakesTheme.colors.backgroundPrimary)
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp, alignment = Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
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
    }
}