import android.content.Context
import android.provider.MediaStore
import java.io.File

fun getAllMusicFiles(context: Context): List<File> {
    val musicFiles = mutableListOf<File>()
    val projection = arrayOf(
        MediaStore.Audio.Media.DATA
    )

    val cursor = context.contentResolver.query(
        MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
        projection,
        "${MediaStore.Audio.Media.IS_MUSIC} != 0",
        null,
        "${MediaStore.Audio.Media.DATE_ADDED} DESC"
    )

    cursor?.use {
        val columnIndex = it.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)
        while (it.moveToNext()) {
            val filePath = it.getString(columnIndex)
            val file = File(filePath)
            if (file.exists()) {
                musicFiles.add(file)
            }
        }
    }

    return musicFiles
}