package jp.ac.kawahara.t_sasaki.mapviewsample

import android.os.Handler
import android.util.Log
import android.widget.TextView
import androidx.annotation.UiThread
import androidx.annotation.WorkerThread
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import java.nio.charset.StandardCharsets
import androidx.lifecycle.lifecycleScope

class OpenWeatherMap {
    companion object {
        private const val WEATHERINFO_URL =
            "https://api.openweathermap.org/data/2.5/weather?lang=ja"
        private const val APP_ID = "64d4dd0bb9c25f6e3179a45bccdf68cf"
    }

    // 参考 https://chatgpt.com/share/6799a891-0854-800b-ae77-40724711c7fb
    private fun getUrl(lon: Double, lat: Double): String {
        val url = "$WEATHERINFO_URL&lon=$lon&lat=$lat&appid=$APP_ID"
        Log.v("MapViewSample", url)
        return url
    }

    @WorkerThread
    private fun fetchJsonString(lon: Double, lat: Double): String {
        var result = ""
        val url = URL(getUrl(lon, lat))
        val con = url.openConnection() as HttpURLConnection
        con.connectTimeout = 1000
        con.readTimeout = 1000
        con.requestMethod = "GET"
        con.connect()
        val stream = con.inputStream
        result = is2String(stream)
        stream.close()
        con.disconnect()
        return result
    }

    private fun getWeather(lon: Double, lat: Double): String {
        val jsonString = fetchJsonString(lon, lat)
        // ルートJSONオブジェクトを生成。
        val rootJSON = JSONObject(jsonString)
        // 都市名文字列を取得。
        val cityName = rootJSON.getString("name")
        // 緯度経度情報JSONオブジェクトを取得。
        val coordJSON = rootJSON.getJSONObject("coord")
        // 緯度情報文字列を取得。
        val latitude = coordJSON.getString("lat")
        // 経度情報文字列を取得。
        val longitude = coordJSON.getString("lon")

        // 天気情報JSON配列オブジェクトを取得。
        val weatherJSONArray = rootJSON.getJSONArray("weather")
        // 現在の天気情報JSONオブジェクトを取得。
        val weatherJSON = weatherJSONArray.getJSONObject(0)
        // 現在の天気情報文字列を取得。
        val weather = weatherJSON.getString("description")
        return weather
    }//getWeather

    @UiThread
    fun updateWeather(lon: Double, lat: Double, textViewWeather: TextView, scope: CoroutineScope){
        scope.launch{
            try {
                val weather = withContext(Dispatchers.IO) {
                    getWeather(lon, lat)
                    //getWeather(lon, lat).also { textViewWeather.text = it }
                }//withContext
                textViewWeather.text = weather
            } catch (e: Exception){
                Log.e("updateWeather", e.localizedMessage)
            }
        }
    }//updateWeather
}//OpenWeatherMap

fun is2String(stream: InputStream): String {
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