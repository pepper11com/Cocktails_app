package com.example.cocktails_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.cocktails_app.ui.screens.CocktailScreens
import com.example.cocktails_app.ui.screens.DetailCocktailView
import com.example.cocktails_app.ui.screens.SearchCocktailView
import com.example.cocktails_app.ui.theme.Cocktails_appTheme
import com.example.cocktails_app.viewmodel.CocktailViewModel
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Cocktails_appTheme {

                CocktailApp()

            }
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CocktailApp() {
    val navController = rememberAnimatedNavController()

    Scaffold(

    ) { innerPadding ->
        NavHostScreen(navController, innerPadding)
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun NavHostScreen(
    navController: NavHostController,
    innerPadding: PaddingValues,
    viewModel: CocktailViewModel = viewModel()
) {
    AnimatedNavHost(
        navController,
        startDestination = CocktailScreens.CocktailScreen.route,
        Modifier.padding(innerPadding)
    ) {
        composable(CocktailScreens.CocktailScreen.route) {
            SearchCocktailView(
                viewModel = viewModel,
                navController = navController
            )
        }
//        composable(CocktailScreens.DetailScreen.route) {
//            DetailCocktailView(
//                viewModel = viewModel,
//                navController = navController
//            )
//
//        }
    }
}




