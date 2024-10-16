// filename: MainActivity.kt

package jp.ac.kawahara.t_sasaki.databasesample

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var _cocktailId = -1
    private var _cocktailName = ""
    private var _helper = DatabaseHelper(this@MainActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<ListView>(R.id.lvCocktail)
            .onItemClickListener = ListItemClickListener()

    }//onCreate

    // 保存ボタンがタップされたときの処理メソッド。
    fun onSaveButtonClick(view: View) {
        // 感想欄を取得。
        val etNote = findViewById<EditText>(R.id.etNote)
        var note = etNote.text.toString()
        var db = _helper.writableDatabase
        var sqlDelete = "DELETE FROM cocktailmemos WHERE _id = ?"
        var stmt = db.compileStatement(sqlDelete)
        stmt.bindLong(1, _cocktailId.toLong())
        stmt.executeUpdateDelete()

        val sqlInsert = "INSERT INTO cocktailmemos (_id, name, note) VALUES (?,?,?)"
        stmt = db.compileStatement(sqlInsert)
        stmt.bindLong(1, _cocktailId.toLong())
        stmt.bindString(2, _cocktailName)
        stmt.bindString(3, note)
        stmt.executeInsert()

        // 感想欄の入力値を消去。
        etNote.setText("")
        // カクテル名を表示するTextViewを取得。
        val tvCocktailName = findViewById<TextView>(R.id.tvCocktailName)
        // カクテル名を「未選択」に変更。
        tvCocktailName.text = getString(R.string.tv_name)
        // 保存ボタンを取得。
        val btnSave = findViewById<Button>(R.id.btnSave)
        // 保存ボタンをタップできないように変更。
        btnSave.isEnabled = false
    }

    override fun onDestroy() {
        _helper.close()
        super.onDestroy()
    }//onDestroy

    // リストがタップされたときの処理が記述されたメンバクラス。
    private inner class ListItemClickListener : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
            // タップされた行番号をプロパティの主キーIDに代入。
            _cocktailId = position
            // タップされた行のデータを取得。これがカクテル名となるので、プロパティに代入。
            _cocktailName = parent.getItemAtPosition(position) as String
            // カクテル名を表示するTextViewを取得。
            val tvCocktailName = findViewById<TextView>(R.id.tvCocktailName)
            // カクテル名を表示するTextViewに表示カクテル名を設定。
            tvCocktailName.text = _cocktailName
            // 保存ボタンを取得。
            val btnSave = findViewById<Button>(R.id.btnSave)
            // 保存ボタンをタップできるように設定。
            btnSave.isEnabled = true

            //既存のデータを取得して表示する
            val db = _helper.readableDatabase
            val sql = "SELECT * FROM cocktailmemos WHERE _id = ${_cocktailId}"
            val cursor = db.rawQuery(sql, null)
            var note = ""
            while (cursor.moveToNext()){
                val idxNote = cursor.getColumnIndex("note")
                note = cursor.getString(idxNote)
            }
            val etNote = findViewById<EditText>(R.id.etNote)
            etNote.setText(note)
        }//onItemClick
    }//ListItemClickListener

}//MainActivity