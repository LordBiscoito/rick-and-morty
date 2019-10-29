package br.sergio.rickandmorty

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.ontbee.legacyforks.cn.pedant.SweetAlert.SweetAlertDialog


object Utils {
    fun sendToActivity(activity: Activity, clazz: Class<*>, json: String = "") {
        val intent = Intent(activity, clazz)

        if (!json.isEmpty()) {
            intent.putExtra("json", json)
        }

        activity.startActivity(intent)
        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    fun showSimpleMessage(context: Context, titleText: String, contentText: String) {
        SweetAlertDialog(context)
            .setTitleText(titleText)
            .setContentText(contentText)
            .show()
    }
}