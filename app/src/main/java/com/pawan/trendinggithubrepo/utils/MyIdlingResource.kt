package com.pawan.trendinggithubrepo.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object MyIdlingResource {
    private val mCountingIdlingResource: CountingIdlingResource =
        CountingIdlingResource("my_idling_resource")

    fun increment() {
        mCountingIdlingResource.increment()
    }

    fun decrement() {
        if (!idlingResource.isIdleNow)
            mCountingIdlingResource.decrement()
    }

    val idlingResource: IdlingResource
        get() = mCountingIdlingResource
}