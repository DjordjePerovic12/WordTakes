package com.bokadev.word_takes


interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

