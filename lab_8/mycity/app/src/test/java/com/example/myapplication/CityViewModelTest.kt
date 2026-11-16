package com.example.myapplication

import com.example.myapplication.data.CityRepository
import com.example.myapplication.ui.CityViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class CityViewModelTest {
    @Test
    fun eachCategoryHasThreeToFiveRecommendations() {
        val repository = CityRepository()

        assertEquals(3, repository.getCategories().size)
        repository.getCategories().forEach { category ->
            val recommendationCount = repository.getPlaces(category.type).size
            assertTrue(
                "${category.type} should contain 3 to 5 places",
                recommendationCount in 3..5
            )
        }
    }

    @Test
    fun selectingCategoryUpdatesPlacesAndClearsSelectedPlace() {
        val viewModel = CityViewModel()
        val initialPlace = viewModel.uiState.value.places.first()
        viewModel.selectPlace(initialPlace)

        val nextCategory = viewModel.uiState.value.categories
            .first { it.type != initialPlace.category }
        viewModel.selectCategory(nextCategory)

        val state = viewModel.uiState.value
        assertEquals(nextCategory, state.selectedCategory)
        assertTrue(state.places.all { it.category == nextCategory.type })
        assertNull(state.selectedPlace)
    }
}
