import android.media.MediaPlayer
import java.io.File

class MusicPlayer {
    private var mediaPlayer: MediaPlayer? = null

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
}