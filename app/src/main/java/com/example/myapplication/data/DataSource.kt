package com.example.myapplication.data

import com.example.myapplication.R

enum class Categories (name: String) {
    FIRST(name = "Высокий ценовой сегмент"), SECOND(name = "Средний ценовой сегмент"), THIRD(name = "Низкий ценовой сегмент")
}
val categories = listOf(
    Category(1, R.string.first, R.drawable.first, Categories.FIRST),
    Category(2, R.string.second, R.drawable.second, Categories.SECOND),
    Category(3, R.string.third, R.drawable.third, Categories.THIRD)
)

val places = listOf(
    Place(1, R.string.rest1, 4.4, R.drawable.rest1, Categories.SECOND, R.string.rest1descr),
    Place(2, R.string.rest2, 4.7, R.drawable.rest2, Categories.SECOND, R.string.rest2descr),
    Place(3, R.string.rest3, 4.5, R.drawable.rest3, Categories.FIRST, R.string.rest3descr),
    Place(4, R.string.rest4, 4.3, R.drawable.rest4,Categories.THIRD, R.string.rest4descr),
    Place(5, R.string.rest5, 4.3, R.drawable.rest5,Categories.THIRD, R.string.rest5descr),
    Place(6, R.string.rest6, 4.8, R.drawable.rest6,Categories.FIRST, R.string.rest6descr),
    Place(7, R.string.rest7, 4.8, R.drawable.rest7,Categories.FIRST, R.string.rest7descr),

    )