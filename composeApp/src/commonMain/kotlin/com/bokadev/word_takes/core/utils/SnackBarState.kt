package com.bokadev.word_takes.core.utils

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class SnackBarState(
    val scaffoldState: SnackbarHostState,
    val scope: CoroutineScope,
    val navController: NavHostController
) {
    private var snackBarJob: Job? = null

    init {
        cancelActiveJob()
    }

    fun showSnackBar(
        message: String
    ) {
        cancelActiveJob()
        snackBarJob = scope.launch {
            scaffoldState.showSnackbar(
                message = message, duration = SnackbarDuration.Long, actionLabel = "OK"
            )
            cancelActiveJob()
        }
    }

    private fun cancelActiveJob() {
        snackBarJob?.let { job ->
            job.cancel()
            snackBarJob = Job()
        }
    }
}

@Composable
fun rememberAppState(
    scaffoldState : SnackbarHostState = remember {
        SnackbarHostState()
    },
    navController : NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope()
) = remember(scaffoldState,navController,scope){
    SnackBarState(
        scaffoldState = scaffoldState,
        navController = navController,
        scope = scope
    )
}