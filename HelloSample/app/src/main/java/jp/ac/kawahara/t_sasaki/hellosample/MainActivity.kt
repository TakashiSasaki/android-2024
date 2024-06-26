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
        //val btClick : Button = findViewById(R.id.btClick)
        val listener = HelloListener()
        btClick.setOnClickListener( listener )

        btClick.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val input = findViewById<EditText>(R.id.etName)
                val output = findViewById<TextView>(R.id.tvOutput)
                val inputStr : String = input.text.toString()
                output.text = inputStr + "さん、こんにちは！"
            }
        })

        btClick.setOnClickListener {
            val input = findViewById<EditText>(R.id.etName)
            val output = findViewById<TextView>(R.id.tvOutput)
            val inputStr: String = input.text.toString()
            output.text = inputStr + "さん、こんにちは！"
        }

        btClick.setOnClickListener(fun(it: View) {
            val input = findViewById<EditText>(R.id.etName)
            val output = findViewById<TextView>(R.id.tvOutput)
            val inputStr: String = input.text.toString()
            output.text = inputStr + "さん、こんにちは！"
        })


        //上の三行と以下の一行は等価
        //findViewById<Button>(R.id.btClick).setOnClickListener(HelloListener())
    }//onCreate

    private inner class HelloListener : View.OnClickListener {
        override fun onClick(v: View) {
            val input = findViewById<EditText>(R.id.etName)
            val output = findViewById<TextView>(R.id.tvOutput)
            val inputStr : String = input.text.toString()
            output.text = inputStr + "さん、こんにちは！"
        }//onClick

    }//HelloListener

    private fun example(){
        val lambda = {x : Int -> x * 2}
        val numbers = listOf(1,2,3)
        val doubled = numbers.map(lambda)
        println(doubled)
    }

}//class MainActivity
