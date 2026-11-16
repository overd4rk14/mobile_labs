/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.cupcake.R
import com.example.cupcake.data.DataSource
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_verifyContent() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()
    }

    @Test
    fun selectOptionScreen_verifyNextButtonEnabledWhenOptionSelected() {
        composeTestRule.setContent {
            SelectOptionScreen(
                subtotal = "$100",
                options = listOf("Vanilla", "Chocolate")
            )
        }

        composeTestRule.onNodeWithText("Chocolate").performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    @Test
    fun startOrderScreen_verifyContent() {
        composeTestRule.setContent {
            StartOrderScreen(
                quantityOptions = DataSource.quantityOptions,
                onNextButtonClicked = {}
            )
        }

        composeTestRule.onNodeWithStringId(R.string.order_cupcakes).assertIsDisplayed()
        DataSource.quantityOptions.forEach { (labelResourceId, _) ->
            composeTestRule.onNodeWithStringId(labelResourceId).assertIsDisplayed()
        }
    }

    @Test
    fun orderSummaryScreen_verifyContent() {
        val orderUiState = OrderUiState(
            quantity = 6,
            flavor = "Chocolate",
            date = "Tue May 21",
            price = "$12.00"
        )

        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onCancelButtonClicked = {},
                onSendButtonClicked = { _, _ -> }
            )
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.quantity).uppercase()
        )
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.resources.getQuantityString(R.plurals.cupcakes, 6, 6)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.flavor).uppercase()
        )
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.pickup_date).uppercase()
        ).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, orderUiState.price)
        ).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.send).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(R.string.cancel).assertIsDisplayed()
    }
}
