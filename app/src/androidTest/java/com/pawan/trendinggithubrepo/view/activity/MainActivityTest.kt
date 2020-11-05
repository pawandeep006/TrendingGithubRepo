package com.pawan.trendinggithubrepo.view.activity


import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.scrollTo
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.pawan.trendinggithubrepo.R
import com.pawan.trendinggithubrepo.adapter.RepoListAdapter
import com.pawan.trendinggithubrepo.utils.MyIdlingResource.idlingResource
import kotlinx.android.synthetic.main.fragment_repo_list.*
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

    private val POSITION: Int = 10

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Before
    fun registerIdlingResource() {
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun unregisterIdlingResource() {
        IdlingRegistry.getInstance().unregister(idlingResource)
    }

    @Test
    fun list_load_click_action_title_test() {

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
    fun list_data_validated_with_details_test() {

        val recyclerView = onView(
            allOf(
                withId(R.id.recyclerView)
            )
        )
        recyclerView.perform(actionOnItemAtPosition<ViewHolder>(POSITION, scrollTo()))

        val mRecyclerView = mActivityTestRule.activity.recyclerView
        val item = (mRecyclerView.adapter as RepoListAdapter).items[POSITION]
        val repoName = item.name

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
                .atPositionOnView(POSITION, R.id.repoName)
        )
            .check(matches(isDisplayed()))

        onView(
            RecyclerViewMatcher(R.id.recyclerView)
                .atPositionOnView(POSITION, R.id.repoName)
        )
            .check(matches(withText(repoName)))

        recyclerView2.perform(actionOnItemAtPosition<ViewHolder>(POSITION, click()))

        val textView2 = onView(
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
        textView2.check(matches(withText(repoName)))
        pressBack()
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
