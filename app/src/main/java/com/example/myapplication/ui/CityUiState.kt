package com.example.myapplication.ui

import com.example.myapplication.data.Category
import com.example.myapplication.data.Place

data class CityUiState(
    val categories: List<Category> = emptyList(),
    val selectedCategory: Category? = null,
    val places: List<Place> = emptyList(),
    val selectedPlace: Place? = null
)
