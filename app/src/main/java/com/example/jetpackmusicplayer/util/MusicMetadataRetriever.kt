import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import com.example.jetpackmusicplayer.data.MusicMetadata
import java.io.File

class MusicMetadataRetriever {
    private val retriever = MediaMetadataRetriever()

    fun getMetadata(file: File): MusicMetadata {
        retriever.setDataSource(file.absolutePath)

        val metadata = MusicMetadata(
            filename = file.nameWithoutExtension,
            title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE),
            album = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM),
            artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST),
            duration = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION),
            artwork = retriever.embeddedPicture?.let {
                BitmapFactory.decodeByteArray(it, 0, it.size)
            }
        )

        retriever.release()

        return metadata
    }
}