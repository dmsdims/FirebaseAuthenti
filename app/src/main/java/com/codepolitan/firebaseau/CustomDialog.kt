package com.codepolitan.firebaseau

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable

object CustomDialog {
    private var dialog: Dialog? = null
//UNTUK MEMANGGIL LAYOUT_PROGRES DICOSTUMDIALOG
    fun showLoading(activity: Activity){
        val dialogView = activity.layoutInflater.inflate(R.layout.layout_progress,null,false)

        dialog = Dialog(activity)
        dialog?.setCancelable(false)
//     FUNGSI UNTUK MENAMPILKAN LAYOUT_PROGRES MENJADI TRANSPARAN
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color. TRANSPARENT))
        dialog?.setContentView(dialogView)

        dialog?.show()

    }
    fun hideLoading(){
        dialog?.dismiss()
    }
}