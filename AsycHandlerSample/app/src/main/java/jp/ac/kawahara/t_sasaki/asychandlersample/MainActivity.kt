package jp.ac.kawahara.t_sasaki.asychandlersample

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.HandlerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.nio.charset.StandardCharsets
import java.util.concurrent.Executor
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    companion object {
        private const val DEBUG_TAG = "AsyncSample"
        private const val WEATHERINFO_URL =
            "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val APP_ID = "64d4dd0bb9c25f6e3179a45bccdf68cf"
    }

    private var _list = mutableListOf<MutableMap<String, String>>()
    // 教科書のように左辺で型を指定してもよい。

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _list = createList()

        val lvCityList = findViewById<ListView>(R.id.lvCityList)
        val from = arrayOf("name")
        val to = intArrayOf(android.R.id.text1)
        val adapter =
            SimpleAdapter(this@MainActivity, _list, android.R.layout.simple_list_item_1, from, to)
        lvCityList.adapter = adapter
        lvCityList.onItemClickListener = ListItemClickListener()

    }//onCreate

    private fun createList(): MutableList<MutableMap<String, String>> {
        var list = mutableListOf<MutableMap<String, String>>()
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
    private fun receiveWeatherInfo(urlFull: String) {
        //既存の天気表示をクリア
        findViewById<TextView>(R.id.tvWeatherDesc).text = ""
        findViewById<TextView>(R.id.tvWeatherTelop).text = ""

        //メインスレッド側でHandlerオブジェクトを用意する
        val handler: Handler = HandlerCompat.createAsync(mainLooper)

        //ここでExecutorServiceを使ってサブスレッドでrunを実行しようとしている
        Executors.newSingleThreadExecutor().submit(
            WeatherInfoBackgroundReceiver(handler, urlFull)
        )
    }

    private inner class WeatherInfoBackgroundReceiver
        (handler: Handler, url: String) : Runnable {
        private val _handler = handler
        private val _url = url

        @WorkerThread
        override fun run() {

            _handler.post(ProgressUpdateRunnable("バックグラウンド処理開始"))

            var result = ""
            val url = URL(_url)
            val con = url.openConnection() as HttpURLConnection
            con.connectTimeout = 1000
            con.readTimeout = 1000
            con.requestMethod = "GET"
            try {

                _handler.post(ProgressUpdateRunnable("Webアクセス開始"))

                con.connect()
                val stream = con.inputStream
                result = is2String(stream)
                stream.close()

                _handler.post(ProgressUpdateRunnable("Webアクセス終了"))

            } catch (ex: SocketTimeoutException) {
                Log.w(DEBUG_TAG, "通信タイムアウト", ex)
                _handler.post(ProgressUpdateRunnable("通信タイムアウト"))
            }//try
            con.disconnect()

            _handler.post(ProgressUpdateRunnable("Webアクセス終了"))

        }//run

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
        }//is2String


    }//WeatherInfoBackgroundReceiver

    @UiThread
    private fun addMsg(msg: String) {
        // tvWeatherDescのTextViewを取得。
        val tvWeatherDesc = findViewById<TextView>(R.id.tvWeatherDesc)
        // 現在表示されているメッセージを取得。
        var msgNow = tvWeatherDesc.text
        // 現在表示されているメッセージが空でなければ、改行を追加。
        if (!msgNow.equals("")) {
            msgNow = "${msgNow}\n"
        }
        // 引数のメッセージを追加。
        msgNow = "${msgNow}${msg}"
        // 追加されたメッセージをTextViewに表示。
        tvWeatherDesc.text = msgNow
    }//addMsg

    private inner class ProgressUpdateRunnable(msg:String):Runnable{
        val _msg = msg
        @UiThread
        override fun run(){
            addMsg(_msg)
        }//run
    }//ProgressUpdateRunnable
}//AppCompatActivity