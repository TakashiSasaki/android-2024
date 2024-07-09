package jp.ac.kawahara.t_sasaki.menusample

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var _menuList : MutableList<MutableMap<String, Any>> = mutableListOf()
    private val _from = arrayOf("name", "price")
    private val _to  = intArrayOf(R.id.tvMenuNameRow, R.id.tvMenuPriceRow)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        _menuList = createTeishokuList()
        val adapter = SimpleAdapter(this@MainActivity,
            _menuList,
            R.layout.row,
            _from, _to)
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        lvMenu.adapter = adapter
        lvMenu.onItemClickListener = ListItemClickListener()
    }// onCreate

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options_menu_list, menu)
        //return super.onCreateOptionsMenu(menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menuListOptionTeishoku->{
                _menuList = createTeishokuList()
            }
            R.id.menuListOptionCurry->{
                _menuList = createCurryList()
            }
            else->{
                return super.onOptionsItemSelected(item)
            }
        }//when
        val adapter = SimpleAdapter(this,
            _menuList, R.layout.row,
            _from, _to)
        val lvMenu = findViewById<ListView>(R.id.lvMenu)
        lvMenu.adapter = adapter
        return true;
    }// onOptionsItemSelected

    private fun createTeishokuList(): MutableList<MutableMap<String, Any>> {
        var menuList : MutableList<MutableMap<String, Any>> = mutableListOf()
        menuList.add(mutableMapOf("name" to "唐揚げ定食",
            "price" to 800,
            "desc" to "若鳥の唐揚げにサラダ、ご飯とお味噌汁が付きます。"))
        menuList.add(mutableMapOf("name" to "ハンバーク定食",
            "price" to 850,
            "desc" to "手ごねハンバーグにサラダ、ご飯とお味噌汁が付きます。"))
        return menuList
    }//createTeishokuList

    private fun createCurryList(): MutableList<MutableMap<String, Any>> {
        var menuList : MutableList<MutableMap<String, Any>> = mutableListOf()
        menuList.add(mutableMapOf("name" to "ビーフカレー",
            "price" to 520,
            "desc" to "特選スパイスをきかせた国産ビーフ100％のカレーです。"))
        menuList.add(mutableMapOf("name" to "ポークカレー",
            "price" to 420,
            "desc" to "特選スパイスをきかせた国産ポーク100％のカレーです。"))
        return menuList
    }//createCurryList

    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val item = parent.getItemAtPosition(position)
                            as MutableMap<String, Any>
            val menuName = item["name"] as String
            val menuPrice = item["price"] as Int

            val intent2MenuThanks = Intent(this@MainActivity,
                MenuThanksActivity::class.java)
            intent2MenuThanks.putExtra("menuName", menuName)
            intent2MenuThanks.putExtra("menuPrice",
                "${menuPrice}円")
            startActivity(intent2MenuThanks)
        }// onItemClick

    } // ListItemClickListener
} // MainActivity
