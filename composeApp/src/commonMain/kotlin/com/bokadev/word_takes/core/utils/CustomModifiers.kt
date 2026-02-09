package com.bokadev.word_takes.core.utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

object CustomModifiers {
    val snackBarHost : @Composable SnackbarHostState.() -> Unit = {
        SnackbarHost(hostState = this){ snackBarData ->
            Snackbar(
                snackbarData = snackBarData,
                containerColor = Color.White,
                contentColor = Color.Black,
                actionColor = Color.Black,
                shape = RoundedCornerShape(4.dp)
            )
        }
    }
}