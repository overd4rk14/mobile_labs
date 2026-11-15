package com.example.myapplication.data

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Place(
    val id: Int,
    @StringRes val title: Int,
    val rating: Double,
    @DrawableRes val imageRes: Int,
    val category: Categories,
    @StringRes val description: Int
)