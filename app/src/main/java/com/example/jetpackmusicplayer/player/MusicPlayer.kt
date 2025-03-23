import android.media.MediaPlayer
import java.io.File

class MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

    fun isPlaying(): Boolean {
        mediaPlayer?.let {
            return it.isPlaying
        }
        return false
    }

    fun play(file: File) {
        stop()
        if (file.exists()) {
            mediaPlayer = MediaPlayer().apply {
                setDataSource(file.absolutePath)
                prepare()
                start()
            }
        }
    }

    fun stop() {
        mediaPlayer?.release()
        mediaPlayer = null
    }

    fun pause() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.pause()
            }
        }
    }

    fun resume() {
        mediaPlayer?.let {
            if (it.isPlaying) {
                it.start()
            }
        }
    }
}