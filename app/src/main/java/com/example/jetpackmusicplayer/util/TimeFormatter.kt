package com.example.jetpackmusicplayer.util

import android.annotation.SuppressLint

@SuppressLint("DefaultLocale")
fun formatTime(ms: Int): String {
    val seconds = ms / 1000
    return String.format("%02d:%02d", seconds / 60, seconds % 60)
}
