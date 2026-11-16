package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.Category
import com.example.myapplication.data.Place
import com.example.myapplication.ui.CityUiState
import com.example.myapplication.ui.CityViewModel
import com.example.myapplication.ui.theme.CityScreen
import com.example.myapplication.ui.theme.DetailScreen
import com.example.myapplication.ui.theme.ExpandedCityScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.ui.theme.utils.CityContentType
import com.example.mycity.ui.theme.CategoryScreen

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val windowSize = calculateWindowSizeClass(this)
                    CityApp(windowSize = windowSize.widthSizeClass)
                }
            }
        }
    }
}

@Composable
fun CityApp(
    windowSize: WindowWidthSizeClass,
    cityViewModel: CityViewModel = viewModel()
) {
    val uiState by cityViewModel.uiState.collectAsStateWithLifecycle()
    val contentType = when (windowSize) {
        WindowWidthSizeClass.Expanded -> CityContentType.LIST_AND_DETAIL
        else -> CityContentType.LIST_ONLY
    }

    if (contentType == CityContentType.LIST_AND_DETAIL) {
        ExpandedCityScreen(
            uiState = uiState,
            onCategorySelected = cityViewModel::selectCategory,
            onPlaceSelected = cityViewModel::selectPlace
        )
    } else {
        CompactCityApp(
            uiState = uiState,
            onCategorySelected = cityViewModel::selectCategory,
            onPlaceSelected = cityViewModel::selectPlace,
            findPlace = cityViewModel::findPlace
        )
    }
}

@Composable
fun CompactCityApp(
    uiState: CityUiState,
    onCategorySelected: (Category) -> Unit,
    onPlaceSelected: (Place) -> Unit,
    findPlace: (Int) -> Place?
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "city"
    ) {
        composable("city") {
            CityScreen(
                navController = navController,
                onCategorySelected = onCategorySelected
            )
        }
        composable("category/{categoryName}") { backStackEntry ->
            val categoryName = backStackEntry.arguments?.getString("categoryName").orEmpty()
            CategoryScreen(
                navController = navController,
                categoryName = categoryName,
                places = uiState.places,
                onPlaceSelected = onPlaceSelected
            )
        }
        composable("detail/{placeId}") { backStackEntry ->
            val placeId = backStackEntry.arguments?.getString("placeId")?.toIntOrNull()
            val place = placeId?.let { id ->
                uiState.selectedPlace?.takeIf { it.id == id } ?: findPlace(id)
            }
            DetailScreen(
                navController = navController,
                place = place
            )
        }
    }
}