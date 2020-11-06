package com.pawan.trendinggithubrepo.view


import android.content.Intent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.LargeTest
import androidx.test.runner.AndroidJUnit4
import com.pawan.trendinggithubrepo.R
import com.pawan.trendinggithubrepo.utils.MockServer
import com.pawan.trendinggithubrepo.utils.MyIdlingResource
import com.pawan.trendinggithubrepo.utils.RecyclerViewMatcher
import com.pawan.trendinggithubrepo.view.activity.MainActivity
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@LargeTest
@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var mockServer: MockWebServer

    @Rule
    @JvmField
    var mActivityTestRule = ActivityScenarioRule<MainActivity>(Intent(ApplicationProvider.getApplicationContext(), MainActivity::class.java))

    @Before
    fun setup() {
        mockServer = MockWebServer()
        mockServer.start(8080)
        IdlingRegistry.getInstance().register(MyIdlingResource.idlingResource)
    }

    @After
    fun teardown() {
        mockServer.shutdown()
        IdlingRegistry.getInstance().unregister(MyIdlingResource.idlingResource)
    }


    @Test
    fun list_load_click_action_title_test() {
        mockServer.dispatcher = MockServer.ResponseDispatcher()
        mActivityTestRule.scenario
        onView(
                RecyclerViewMatcher(R.id.recyclerView)
                        .atPositionOnView(0, R.id.repoName)
        )
                .check(matches(isDisplayed()));

        val recyclerView = onView(
                allOf(
                        withId(R.id.recyclerView),
                        childAtPosition(
                                withId(R.id.frameLayout),
                                0
                        )
                )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(0, click()))

        val textView = onView(
                allOf(
                        withParent(
                                allOf(
                                        withId(R.id.action_bar),
                                        withParent(withId(R.id.action_bar_container))
                                )
                        ),
                        isDisplayed()
                )
        )
        textView.check(matches(withText("flutter")))

        val textView2 = onView(
                allOf(
                        withId(R.id.fullName),
                        withParent(
                                allOf(
                                        withId(R.id.frameLayout),
                                        withParent(withId(R.id.container))
                                )
                        ),
                        isDisplayed()
                )
        )
        textView2.check(matches(withText("flutter/flutter")))

        pressBack()

        val textView3 = onView(
                allOf(
                        withParent(
                                allOf(
                                        withId(R.id.action_bar),
                                        withParent(withId(R.id.action_bar_container))
                                )
                        ),
                        isDisplayed()
                )
        )
        textView3.check(matches(withText("Trending Github Repos")))
    }

    @Test
    fun no_data_api_test() {

        mockServer.dispatcher = MockServer.ErrorDispatcher()
        mActivityTestRule.scenario
        onView(withId(android.R.id.message)).inRoot(isDialog()).check(matches(isDisplayed()))
        Espresso.pressBack()
    }

    @Test
    fun list_data_validated_with_details_test() {

        mockServer.dispatcher = MockServer.ResponseDispatcher()
        mActivityTestRule.scenario
        val position = 3
        val repoName = "Awesome-Hacking"

        val recyclerView = onView(
                allOf(
                        withId(R.id.recyclerView)
                )
        )
        recyclerView.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        position,
                        ViewActions.scrollTo()
                )
        )

        val recyclerView2 = onView(
                allOf(
                        withId(R.id.recyclerView),
                        childAtPosition(
                                withId(R.id.frameLayout),
                                0
                        )
                )
        )

        onView(
                RecyclerViewMatcher(R.id.recyclerView)
                        .atPositionOnView(
                                position,
                                R.id.repoName
                        )
        )
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        onView(
                RecyclerViewMatcher(R.id.recyclerView)
                        .atPositionOnView(
                                position,
                                R.id.repoName
                        )
        )
                .check(ViewAssertions.matches(ViewMatchers.withText(repoName)))

        recyclerView2.perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                        position,
                        ViewActions.click()
                )
        )

        val textView2 = onView(
                allOf(
                        ViewMatchers.withParent(
                                allOf(
                                        withId(R.id.action_bar),
                                        ViewMatchers.withParent(withId(R.id.action_bar_container))
                                )
                        ),
                        ViewMatchers.isDisplayed()
                )
        )
        textView2.check(ViewAssertions.matches(ViewMatchers.withText(repoName)))
        Espresso.pressBack()
    }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
}
