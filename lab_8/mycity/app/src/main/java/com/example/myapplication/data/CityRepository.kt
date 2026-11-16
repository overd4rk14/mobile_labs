package com.example.myapplication.data

class CityRepository {
    fun getCategories(): List<Category> = categories

    fun getPlaces(category: Categories): List<Place> =
        places.filter { it.category == category }

    fun getPlace(id: Int): Place? = places.find { it.id == id }
}
