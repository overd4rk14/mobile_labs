package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import com.example.myapplication.data.Category
import com.example.myapplication.data.CityRepository
import com.example.myapplication.data.Place
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CityViewModel(
    private val repository: CityRepository = CityRepository()
) : ViewModel() {
    private val initialCategory = repository.getCategories().firstOrNull()
    private val _uiState = MutableStateFlow(
        CityUiState(
            categories = repository.getCategories(),
            selectedCategory = initialCategory,
            places = initialCategory?.let { repository.getPlaces(it.type) }.orEmpty()
        )
    )
    val uiState = _uiState.asStateFlow()

    fun selectCategory(category: Category) {
        _uiState.update {
            it.copy(
                selectedCategory = category,
                places = repository.getPlaces(category.type),
                selectedPlace = null
            )
        }
    }

    fun selectPlace(place: Place) {
        _uiState.update { it.copy(selectedPlace = place) }
    }

    fun findPlace(id: Int): Place? = repository.getPlace(id)
}
