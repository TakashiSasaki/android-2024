package jp.ac.kawahara.t_sasaki.mediasample

import android.media.MediaPlayer
import android.media.MediaPlayer.MEDIA_INFO_VIDEO_TRACK_LAGGING
import android.media.MediaPlayer.OnCompletionListener
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    var _player: MediaPlayer? = null

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

        _player?.let {
            it.setDataSource(this, mediaFileUri)
            it.setOnPreparedListener(PlayerPreparedListener())
            it.setOnCompletionListener(PlayerCompletionListener())
            it.prepareAsync()
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

    fun onPlayButtonClick(v: View) {
        val btPlay = findViewById<Button>(R.id.btPlay)
        _player?.let {
            if (it.isPlaying) {
                it.pause()
                btPlay.setText(R.string.bt_play_play)
            } else {
                it.start()
                btPlay.setText(R.string.bt_play_pause)
            }//if
        }//let
    }//onPlayButtonClick

    fun onBackButtonClick(v: View) {
        _player?.seekTo(0)
    }//onBackButtonClick

    fun onForwardButtonClick(v: View) {
        _player?.let {
            it.seekTo(it.duration)
            if (!it.isPlaying) {
                findViewById<Button>(R.id.btPlay).setText(R.string.bt_play_pause)
                it.start()
            }//if
        }//let
    }//onForwardButtonClick

    override fun onStop() {
        _player?.let {
            if (it.isPlaying) {
                it.stop()
            }
            it.release()
        }//let
        super.onStop()
    }//onStop

}//MainActivity
