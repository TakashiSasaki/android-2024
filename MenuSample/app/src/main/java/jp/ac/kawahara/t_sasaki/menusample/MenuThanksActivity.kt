package jp.ac.kawahara.t_sasaki.menusample

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MenuThanksActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_thanks)

        val menuName = intent.getStringExtra("menuName")
        val menuPrice = intent.getStringExtra("menuPrice")

        findViewById<TextView>(R.id.tvMenuName).text = menuName
        findViewById<TextView>(R.id.tvMenuPrice).text = menuPrice
    }

    fun onBackButtonClick(v: View){
        finish()
    }

}