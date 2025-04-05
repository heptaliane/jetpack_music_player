import android.content.Context
import android.media.MediaScannerConnection
import android.util.Log
import java.io.File

fun scanMusicDirectory(context: Context) {
    val musicDir = File("/storage/emulated/0/Music")
    val paths = musicDir.listFiles().map {
        it.absolutePath
    }.toTypedArray()

    if (!paths.isNullOrEmpty()) {
        MediaScannerConnection.scanFile(context, paths, null) { path, uri ->
            Log.d("jplayer", "scanned: ${path} -> ${uri}")
        }
    }
}