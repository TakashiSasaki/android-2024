package jp.ac.kawahara.t_sasaki.listviewsample

import android.app.LauncherActivity.ListItem
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        //lvMenu.onItemClickListener = ListItemClickListener()
        lvMenu.setOnItemClickListener(ListItemClickListener())
    }//onCreate

    private inner class ListItemClickListener : AdapterView.OnItemClickListener{
        override fun onItemClick
                    (parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item  = parent.getItemAtPosition(position) as String
            //val item = (view as TextView).text.toString()
            val show = "あなたが選んだ定食：$item"
            val toast : Toast = Toast.makeText(this@MainActivity, show, Toast.LENGTH_LONG)
            //val toast : Toast = Toast.makeText(applicationContext, show, Toast.LENGTH_LONG)
            toast.show()
        }// onItemClick

    }//ListItemClickListener

}//MainActivity
