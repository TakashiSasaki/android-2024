package jp.ac.kawahara.t_sasaki.listviewsample2

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment

class OrderConfirmDialogFragment :  DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // アクティビティがnullでないならばダイアログオブジェクトを生成。
        val dialog : AlertDialog? = activity?.let {
            val builder = AlertDialog.Builder(it)
            // ダイアログのタイトルを設定。
            builder.setTitle(R.string.dialog_title)
            // ダイアログのメッセージを設定。
            builder.setMessage(R.string.dialog_msg)
            // Positive Buttonを設定。
            builder.setPositiveButton(R.string.dialog_btn_ok, DialogButtonClickListener())
            // Negative Buttonを設定。
            builder.setNegativeButton(R.string.dialog_btn_ng, DialogButtonClickListener())
            // Neutral Buttonを設定。
            builder.setNeutralButton(R.string.dialog_btn_nu, DialogButtonClickListener())
            // ダイアログオブジェクトを生成。
            builder.create()
        }// let
        // 生成したダイアログオブジェクトをリターン。
        return dialog ?: throw IllegalStateException("アクティビティがnullです")
    }// onCreateDialog

    private inner class DialogButtonClickListener
        : DialogInterface.OnClickListener{
        // DialogButtonClickListener
        override fun onClick(dialog: DialogInterface?, which: Int) {
            var msg = ""
            // タップされたアクションボタンで分岐。
            when(which) {
                // Positive Buttonならば…
                DialogInterface.BUTTON_POSITIVE ->
                    // 注文用のメッセージを格納。
                    msg = getString(R.string.dialog_ok_toast)
                // Negative Buttonならば…
                DialogInterface.BUTTON_NEGATIVE ->
                    // キャンセル用のメッセージを格納。
                    msg = getString(R.string.dialog_ng_toast)
                // Neutral Buttonならば…
                DialogInterface.BUTTON_NEUTRAL ->
                    // 問合せ用のメッセージを格納。
                    msg = getString(R.string.dialog_nu_toast)
            }
            // トーストの表示。
            // activity のフルネーム？はthis@OrderConfirmDialogFragment.activity
            Toast.makeText(activity, msg, Toast.LENGTH_LONG).show()
        }// fun onClick
    }// class DialogButtonClickListener


}// class OrderConfirmDialogFragment
