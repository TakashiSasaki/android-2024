package jp.ac.kawahara.t_sasaki.listviewsample2

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var menuList = mutableListOf("から揚げ定食", "ハンバーグ定食", "生姜焼き定食",
            "ステーキ定食", "野菜炒め定食", "とんかつ定食", "ミンチかつ定食", "チキンカツ定食",
            "コロッケ定食", "回鍋肉定食", "麻婆豆腐定食", "青椒肉絲定食", "八宝菜定食", "酢豚定食",
            "豚の角煮", "焼き鳥", "焼き魚定食", "焼肉定食")

    }
}