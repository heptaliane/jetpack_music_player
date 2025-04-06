import android.content.Context
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import androidx.core.net.toUri
import com.example.jetpackmusicplayer.data.MusicMetadata
import java.io.File


fun retrieveMusicMetadata(context: Context, file: File): MusicMetadata {
    val retriever = MediaMetadataRetriever()
    val uri = "file://${file.absolutePath}".toUri()
    retriever.setDataSource(context, uri)

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