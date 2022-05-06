package com.microsoft.device.display.samples.app_views

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.microsoft.device.dualscreen.testing.createWindowLayoutInfoPublisherRule
import com.microsoft.device.dualscreen.testing.simulateVerticalFoldingFeature

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ViewsTest {
    private val activityRule = ActivityScenarioRule(MainActivity::class.java)
    private val publisherRule = createWindowLayoutInfoPublisherRule()

    @get:Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(activityRule)
    }

    @Test
    fun testVerticalFoldingFeature() {
        onView(withText("pane 1")).check(matches(isDisplayed()))

        // 1. Simulate a folding feature
        publisherRule.simulateVerticalFoldingFeature(activityRule)

        // 2. Assert that UI has changed as expected
        onView(withText("pane 1")).check(matches(isDisplayed()))
        onView(withText("pane 2")).check(matches(isDisplayed()))
    }
}