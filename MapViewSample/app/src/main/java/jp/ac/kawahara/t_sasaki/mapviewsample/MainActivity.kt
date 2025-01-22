package jp.ac.kawahara.t_sasaki.mapviewsample

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SupportMapFragmentを取得し、マップの準備ができたらコールバックを設定
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(MapReadyCallback())
    }

    // マップの初期化が終わったときに呼ばれる
    inner class MapReadyCallback : OnMapReadyCallback {
        override fun onMapReady(googleMap: GoogleMap) {
            // 初期表示位置を設定（例として東京駅）
            val tokyoStation = LatLng(35.681236, 139.767125)
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(tokyoStation, 15f))
            // 地図のタップイベントを設定
            googleMap.setOnMapClickListener(MapClickListener())
        }
    }

    // OnMapClickListenerを内部クラスとして定義
    inner class MapClickListener : GoogleMap.OnMapClickListener {
        override fun onMapClick(latLng: LatLng) {
            // TextViewに表示
            findViewById<TextView>(R.id.textViewLatitude).text = "${latLng.latitude}"
            findViewById<TextView>(R.id.textViewLongitude).text = "${latLng.longitude}"
        }
    }
}