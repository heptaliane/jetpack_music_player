package com.example.jetpackmusicplayer.data

import android.graphics.Bitmap

data class MusicMetadata(
    val filename: String,
    val title: String?,
    val album: String?,
    val artist: String?,
    val duration: String?,
    val artwork: Bitmap?,
)