package jp.ac.kawahara.t_sasaki.hellosample

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btClick = findViewById<Button>(R.id.btClick)
        val listener = HelloListener()
        btClick.setOnClickListener( listener )
        val btClear = findViewById<Button>(R.id.btClear)
        btClear.setOnClickListener( listener )
    }//onCreate

    private inner class HelloListener : View.OnClickListener {
        override fun onClick(v: View) {

            when(v.id){
                R.id.btClick ->{
                    val input = findViewById<EditText>(R.id.etName)
                    val output = findViewById<TextView>(R.id.tvOutput)
                    val inputStr : String = input.text.toString()
                    output.text = inputStr + "さん、こんにちは！"
                }//btClick

                R.id.btClear ->{
                    val input = findViewById<EditText>(R.id.etName)
                    input.setText("")
                    val output = findViewById<TextView>(R.id.tvOutput)
                    output.text = ""
                    throw Error("hogehoge")
                }//btClear
            }//when
        }//onClick

    }//HelloListener

}//class MainActivity
