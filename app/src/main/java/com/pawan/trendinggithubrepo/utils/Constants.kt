package com.pawan.trendinggithubrepo.utils

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AlertDialog

const val CONNECT_TIMEOUT = 60L
const val READ_TIMEOUT = 60L
const val WRITE_TIMEOUT = 60L
const val BASE_URL = "https://api.github.com/"

fun String.log() {
    Log.e("TAG", this)
}

fun String.alert(context: Context?) {
    context?.let {
        AlertDialog.Builder(it).setMessage(this).setPositiveButton("OK", null).show()
    }

}