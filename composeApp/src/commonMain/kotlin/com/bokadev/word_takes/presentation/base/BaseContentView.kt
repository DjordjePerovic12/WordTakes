package com.bokadev.word_takes.presentation.base

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import com.bokadev.word_takes.core.utils.noRippleClickable

@Composable
fun BaseContentView(
    modifier: Modifier = Modifier,
    state: BaseState,
    topBar : @Composable () -> Unit = {},
    fab : @Composable () -> Unit = {},
    content : @Composable (BaseState) -> Unit
){
    val focusManager = LocalFocusManager.current

    val customModifier = remember {
        modifier
            .fillMaxSize()
            .noRippleClickable{
                focusManager.clearFocus()
            }
        //TODO add topbar and navigation bar handling
    }

    Scaffold(
        modifier = customModifier,
        topBar = topBar,
        floatingActionButton = fab
    ){ innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ){
            content(state)
            if(state.isLoading){
                Box(modifier = Modifier.fillMaxSize().background(Color.White)){
                    CircularProgressIndicator(
                        modifier = Modifier.align(
                            alignment = Alignment.Center
                        ),
                        color = Color.Black
                    )
                }
            }
        }
        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
    }
}