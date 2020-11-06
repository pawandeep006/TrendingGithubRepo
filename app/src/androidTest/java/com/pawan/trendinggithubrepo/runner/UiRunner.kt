package com.pawan.trendinggithubrepo.runner

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.StrictMode
import androidx.test.runner.AndroidJUnitRunner
import com.pawan.trendinggithubrepo.TestBaseApp

open class UiRunner : AndroidJUnitRunner() {

    override fun onCreate(arguments: Bundle?) {
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
        super.onCreate(arguments)
    }

    override fun newApplication(
            cl: ClassLoader?,
            className: String?,
            context: Context?
    ): Application {
        return super.newApplication(cl, TestBaseApp::class.java.name, context)
    }
}