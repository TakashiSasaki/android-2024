package jp.ac.kawahara.t_sasaki.asyncsample

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    companion object {
        private const val DEBUG_TAG = "AsyncSample"
        private const val WEATHERINFO_URL =
            "https //api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val APP_ID = "64d4dd0bb9c25f6e3179a45bccdf68cf"
    }

    private var _list = mutableListOf<MutableMap<String,String>>()
    // 教科書のように左辺で型を指定してもよい。

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _list = createList()

        val lvCityList = findViewById<ListView>(R.id.lvCityList)
        val from = arrayOf("name")
        val to = intArrayOf(android.R.id.text1)
        val adapter = SimpleAdapter(this@MainActivity, _list, android.R.layout.simple_list_item_1, from, to)
        lvCityList.adapter = adapter
        lvCityList.onItemClickListener = ListItemClickListener()

    }//onCreate

    private fun createList() : MutableList<MutableMap<String,String>>{
        var list = mutableListOf<MutableMap<String,String>>()
        list.add(mutableMapOf("name" to "大阪", "q" to "Osaka"))
        list.add(mutableMapOf("name" to "神戸", "q" to "Kobe"))
        list.add(mutableMapOf("name" to "松山", "q" to "Matsuyama"))
        list.add(mutableMapOf("name" to "東京", "q" to "Tokyo"))
        return list
    }//createList

    // リストがタップされたときの処理が記述されたリスナクラス。
    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            val item = _list.get(position)
            val q = item.get("q")
            q?.let {
                val urlFull = "$WEATHERINFO_URL&q=$q&appid=$APP_ID"
                receiveWeatherInfo(urlFull)
            }
        }
    }

    private fun receiveWeatherInfo(urlFull: String){
    }

}//MainActivity

