package jp.ac.kawahara.t_sasaki.intentsample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 教科書の書き方
        //val menuList2 : MutableList<MutableMap<String,String>> = mutableListOf()
        // 別の書き方
        val menuList = mutableListOf<MutableMap<String, String>>()
        menuList.add(mutableMapOf("name" to "唐揚げ定食", "price" to "800円"))
        menuList.add(mutableMapOf("name" to "ハンバーグ定食", "price" to "850円"))
        menuList.add(mutableMapOf("name" to "生姜焼き定食", "price" to "850円"))
        menuList.add(mutableMapOf("name" to "ステーキ定食", "price" to "1000円"))

        val from = arrayOf("name", "price")
        val to = intArrayOf(android.R.id.text1, android.R.id.text2)
        val adapter = SimpleAdapter(
            this@MainActivity,
            menuList,
            android.R.layout.simple_list_item_2,
            from,
            to)

        findViewById<ListView>(R.id.lvMenu).adapter = adapter
        findViewById<ListView>(R.id.lvMenu).onItemClickListener =
            ListItemClickListener()
    }//onCreate

    private inner class ListItemClickListener : OnItemClickListener{
        override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position)
                            as MutableMap<String, String>
            val menuName : String? = item["name"]
            val menuPrice : String? = item["price"]

            val intent2MenuThanks = Intent(this@MainActivity,
                    MenuThanksActivity::class.java)
            intent2MenuThanks.putExtra("menuName", menuName)
            intent2MenuThanks.putExtra("menuPrice", menuPrice)
            startActivity(intent2MenuThanks)
        }

    }












}//MainActivity
