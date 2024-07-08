package jp.ac.kawahara.t_sasaki.menusample

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var _menuList : MutableList<MutableMap<String, Any>> = mutableListOf()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }// onCreate

    private fun createTeishokuList(){
        var menuList : MutableList<MutableMap<String, Any>> = mutableListOf()
        menuList.add(mutableMapOf("name" to "唐揚げ定食",
            "price" to 800,
            "desc" to "若鳥の唐揚げにサラダ、ご飯とお味噌汁が付きます。"))
        menuList.add(mutableMapOf("name" to "ハンバーク定食",
            "price" to 850,
            "desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。"))

    }
    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position)
                    as MutableMap<String, Any>
            val menuName = item["name"] as String
            val menuPrice = item["price"] as Int

            val intent2MenuThanks = Intent(this@MainActivity,
                MenuThanksActivity::class.java)
            intent2MenuThanks.putExtra("menuName", menuName)
            intent2MenuThanks.putExtra("menuPrice", "${menuPrice}円")
            startActivity(intent2MenuThanks)
        }

    }
}