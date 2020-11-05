package com.pawan.trendinggithubrepo.view.activity

import android.os.Bundle
import android.widget.FrameLayout
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.DaggerAppCompatActivity


abstract class BaseActivity : DaggerAppCompatActivity() {

    @get:LayoutRes
    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
    }

    fun replaceFragment(container: FrameLayout, fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .addToBackStack(fragment.javaClass.canonicalName)
            .replace(container.id, fragment, fragment.javaClass.canonicalName)
            .commit()
    }
}