package jp.ac.kawahara.t_sasaki.mediasample

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var _player : MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
        //    val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
        //    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
        //    insets
        //}

        _player = MediaPlayer()
        val mediaFileUriStr =
            "android.resource://${packageName}/${R.raw.mountain_stream}"
        Log.i("MediaSample", mediaFileUriStr)
        val mediaFileUri = Uri.parse(mediaFileUriStr)

        _player?.let{
            it.setDataSource(this, mediaFileUri)
            
        }
    }
}
