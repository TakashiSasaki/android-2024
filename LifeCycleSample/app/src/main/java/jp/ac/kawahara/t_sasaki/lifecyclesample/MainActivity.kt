package jp.ac.kawahara.t_sasaki.lifecyclesample

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i("LifeCycleSample", "Main onCreate() called.")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }// onCreate

    override fun onStart() {
        Log.i("LifeCycleSample", "Main onStart() called.")
        super.onStart()
    }// onStart

    override fun onRestart() {
        Log.i("LifeCycleSample", "Main onRestart() called.")
        super.onRestart()
    }// onRestart

    override fun onResume() {
        Log.i("LifeCycleSample", "Main onResume() called.")
        super.onResume()
    }// onResume

    override fun onPause() {
        Log.i("LifeCycleSample", "Main onPause() called.")
        super.onPause()
    }// onPause

    override fun onStop() {
        Log.i("LifeCycleSample", "Main onStop() called.")
        super.onStop()
    }// onStop

    override fun onDestroy() {
        Log.i("LifeCycleSample", "Main onDestroy() called.")
        super.onDestroy()
    }// onDestroy

    fun onButtonClick(v: View){
        val intent = Intent(this@MainActivity, SubActivity::class.java)
        startActivity(intent)
    }// onButtonClick

}