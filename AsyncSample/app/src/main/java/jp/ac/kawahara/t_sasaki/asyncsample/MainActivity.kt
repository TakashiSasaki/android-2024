package jp.ac.kawahara.t_sasaki.asyncsample

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.activity.enableEdgeToEdge
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Callable
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    companion object {
        private const val DEBUG_TAG = "AsyncSample"
        private const val WEATHERINFO_URL =
            "https://api.openweathermap.org/data/2.5/weather?lang=ja"
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
                Log.d(DEBUG_TAG, urlFull)
                receiveWeatherInfo(urlFull)
            }//let
        }//onItemClick
    }//ListItemClickListener

    @UiThread
    private fun receiveWeatherInfo(urlFull: String){
        val backgroundReceiver = WeatherInfoBackgroundReceiver(urlFull)
        val executeService = Executors.newSingleThreadExecutor()
        val future = executeService.submit(backgroundReceiver)
        // もしここでメインスレッド側の処理をいろいろやるなら意味があるが。。
        val result = future.get()  //call の終了までブロックされる
    }//receiveWeatherInfo

    private inner class WeatherInfoBackgroundReceiver(url:String) : Callable<String> {
        private val _url = url

        @UiThread
        override fun call(): String {
            var result = ""
            val url = URL(_url)
            val con = url.openConnection() as HttpURLConnection
            con.connectTimeout = 1000
            con.readTimeout = 1000
            con.requestMethod = "GET"
            try{
                con.connect()
                val stream = con.inputStream
                result = is2String(stream)
                stream.close()
            } catch (ex : SocketTimeoutException){
                Log.w(DEBUG_TAG, "通信タイムアウト", ex)
            }
            con.disconnect()
            return result
        }//call

        private fun is2String(stream: InputStream): String {
            val sb = StringBuilder()
            val reader = BufferedReader(
                InputStreamReader(stream, StandardCharsets.UTF_8)
            )
            var line = reader.readLine()
            while (line != null) {
                sb.append(line)
                line = reader.readLine()
            }
            reader.close()
            return sb.toString()
        }

    }//WeatherInfoBackgroundReceiver



}//MainActivity

