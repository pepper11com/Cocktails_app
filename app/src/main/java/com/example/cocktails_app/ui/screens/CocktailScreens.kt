package com.example.cocktails_app.ui.screens

sealed class CocktailScreens(
    val route: String
) {
    object CocktailScreen : CocktailScreens("cocktail_screen")
    object DetailScreen : CocktailScreens("detail_screen")
}
