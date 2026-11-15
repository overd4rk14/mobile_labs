/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.reply.test

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.reply.R
import com.example.reply.data.local.LocalEmailsDataProvider
import com.example.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun compactDevice_selectedEmailRetainedAfterConfigChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }
        val selectedEmail = LocalEmailsDataProvider.allEmails[2]
        val selectedEmailBody = composeTestRule.activity.getString(selectedEmail.body)

        composeTestRule.onNodeWithText(selectedEmailBody).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(selectedEmail.subject)
        ).performClick()
        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.navigation_back)
            .assertExists()
        composeTestRule.onNodeWithText(selectedEmailBody).assertExists()

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithContentDescriptionForStringId(R.string.navigation_back)
            .assertExists()
        composeTestRule.onNodeWithText(selectedEmailBody).assertExists()
    }

    @Test
    fun expandedDevice_selectedEmailRetainedAfterConfigChange() {
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }
        val selectedEmail = LocalEmailsDataProvider.allEmails[2]
        val selectedEmailBody = composeTestRule.activity.getString(selectedEmail.body)

        composeTestRule.onNodeWithText(selectedEmailBody).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(selectedEmail.subject)
        ).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen)
            .onChildren()
            .assertAny(hasAnyDescendant(hasText(selectedEmailBody)))

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTagForStringId(R.string.details_screen)
            .onChildren()
            .assertAny(hasAnyDescendant(hasText(selectedEmailBody)))
    }
}
