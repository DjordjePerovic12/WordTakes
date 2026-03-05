package com.bokadev.word_takes.presentation.stats

sealed class StatsEvent {
    data object Refresh : StatsEvent()
    data class OnRateWordClick(
        val wordId: Int,
        val reaction: String
    ) : StatsEvent()


    data class OnSeeRatingsClick(val selectedWord: String) : StatsEvent()
    data class OpenBottomSheet(val wordId: Int, val selectedWord: String) : StatsEvent()

    data object DismissBottomSheet : StatsEvent()

    data object LoadRatingsNextPage : StatsEvent()
}