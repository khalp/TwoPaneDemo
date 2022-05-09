package com.microsoft.device.display.samples.app_compose

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import com.microsoft.device.display.samples.app_compose.ui.theme.Blue
import com.microsoft.device.display.samples.app_compose.ui.theme.ExampleTheme
import com.microsoft.device.display.samples.app_compose.ui.theme.Red
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.testing.compose.simulateHorizontalFoldingFeature
import com.microsoft.device.dualscreen.testing.createWindowLayoutInfoPublisherRule
import com.microsoft.device.dualscreen.testing.spanFromStart
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = createWindowLayoutInfoPublisherRule()
    private val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

    @get:Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
    }

    /**
     * Basic UI test
     *
     * Tests that "pane 1" text is displayed
     */
    @Test
    fun app_pane1TextShows() {
        composeTestRule.setContent {
            ExampleTheme {
                ExampleApp()
            }
        }

        composeTestRule.onNodeWithText("pane 1").assertIsDisplayed()
    }

    /**
     * Foldable UI test
     *
     * Uses Test Kit (based on Jetpack Window Manager testing library) to simulate a horizontal folding
     * feature and assert that both "pane 1" and "pane 2" are displayed
     */
    @Test
    fun app_horizontalFoldingFeature_pane1AndPane2TextShows() {
        composeTestRule.setContent {
            ExampleTheme {
                ExampleApp()
            }
        }

        composeTestRule.onNodeWithText("pane 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("pane 2").assertDoesNotExist()

        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        composeTestRule.onNodeWithText("pane 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("pane 2").assertIsDisplayed()
    }

    /**
     * Surface Duo UI test
     *
     * Uses the Test Kit (based on UiAutomator) to span the app and test that "pane 1" and "pane 2" are displayed
     */
    @Test
    fun testSpan() {
        composeTestRule.setContent {
            ExampleTheme {
                ExampleApp()
            }
        }

        composeTestRule.onNodeWithText(composeTestRule.getString(R.string.pane1)).assertIsDisplayed()

        // 1. Perform swipe gesture
        device.spanFromStart()

        // 2. Assert that UI has changed as expected
        composeTestRule.onNodeWithText("pane 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("pane 2").assertIsDisplayed()
    }

    /**
     * Custom semantics property test
     *
     * Tests that the custom BackgroundColor semantics property is blue for pane 1 and gray for pane 2
     */
    @Test
    fun app_verticalFoldingFeature_pane1BlueAndPane2Gray() {
        composeTestRule.setContent {
            ExampleTheme {
                ExampleApp()
            }
        }

        publisherRule.simulateHorizontalFoldingFeature(composeTestRule)

        composeTestRule.onNodeWithText("pane 1").assertBackgroundColorEquals(Blue)
        composeTestRule.onNodeWithText("pane 2").assertBackgroundColorEquals(Red)
    }

    private fun SemanticsNodeInteraction.assertBackgroundColorEquals(backgroundColor: Color) =
        assert(SemanticsMatcher.expectValue(BackgroundColorKey, backgroundColor))
}