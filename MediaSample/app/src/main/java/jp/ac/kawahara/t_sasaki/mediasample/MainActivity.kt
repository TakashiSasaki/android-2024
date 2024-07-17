package jp.ac.kawahara.t_sasaki.mediasample

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
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
            
        }//let
    }//onCreate

    private inner class PlayerPreparedListener : OnPreparedListener {
        override fun onPrepared(mp: MediaPlayer?) {
            findViewById<Button>(R.id.btBack).isEnabled = true
            findViewById<Button>(R.id.btPlay).isEnabled = true
            findViewById<Button>(R.id.btForward).isEnabled = true
        }//onPrepared
    }//PlayerPreparedListener

    private inner class PlayerCompletionListener : OnCompletionListener {
        override fun onCompletion(mp: MediaPlayer?) {
            findViewById<Button>(R.id.btPlay).setText(R.string.bt_play_play)
        }//onCompletion
    }//PlayerCompletionListener

}//MainActivity
