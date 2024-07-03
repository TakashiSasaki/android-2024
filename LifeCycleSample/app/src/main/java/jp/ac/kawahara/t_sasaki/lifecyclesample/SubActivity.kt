package jp.ac.kawahara.t_sasaki.lifecyclesample

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SubActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("LifeCycleSample", "Sub onCreate() called.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sub)
    }

    override fun onStart() {
        Log.i("LifeCycleSample", "Sub onStart() called.")
        super.onStart()
    }// onStart

    override fun onRestart() {
        Log.i("LifeCycleSample", "Sub onRestart() called.")
        super.onRestart()
    }// onRestart

    override fun onResume() {
        Log.i("LifeCycleSample", "Sub onResume() called.")
        super.onResume()
    }// onResume

    override fun onPause() {
        Log.i("LifeCycleSample", "Sub onPause() called.")
        super.onPause()
    }// onPause

    override fun onStop() {
        Log.i("LifeCycleSample", "Sub onStop() called.")
        super.onStop()
    }// onStop

    override fun onDestroy() {
        Log.i("LifeCycleSample", "Sub onDestroy() called.")
        super.onDestroy()
    }// onDestroy

    fun onButtonClick(v: View){
        finish()
    }
}