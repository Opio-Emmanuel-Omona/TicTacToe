package com.example.tictactoe.gamePlay

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import com.example.tictactoe.R
import org.hamcrest.Matchers.not
import org.junit.*

import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity>
            = ActivityTestRule(MainActivity::class.java)

    @Before
    fun setup() {
        registerIdlingResource()
        onView(withText(android.R.string.ok)).perform(click())
    }

    @After
    fun teardown() {
        unregisterIdlingResource()
    }

    @Test
    fun clickButtonToPlay() {
        onView(withId(R.id.button_4)).check(matches(withText("")))
        onView(withId(R.id.button_4)).perform(click())
        onView(withId(R.id.button_4)).check(matches(not(withText(""))))
    }


    @Test
    fun buttonIsDisabledAfterPlayed() {
        onView(withId(R.id.button_4)).check(matches(isEnabled()))
        onView(withId(R.id.button_4)).perform(click())
        onView(withId(R.id.button_4)).check(matches(not(isEnabled())))
    }

    private fun registerIdlingResource() {
        val idlingResource = activityRule.activity.getCountingIdlingResource()
        IdlingRegistry.getInstance().register(idlingResource)
    }

    private fun unregisterIdlingResource() {
        val idlingResource = activityRule.activity.getCountingIdlingResource()
        IdlingRegistry.getInstance().unregister(idlingResource)
    }
}
