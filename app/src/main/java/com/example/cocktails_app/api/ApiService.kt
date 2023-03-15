package com.example.cocktails_app.api

import com.example.cocktails_app.datamodel.DrinksResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://www.thecocktaildb.com/api/json/v1/1/search.php?s={drink name}
    @GET("/api/json/v1/1/search.php?s=")
    suspend fun getDrinksByName(
        @Query("s") drinkName: String
    ) : DrinksResponse


    //https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i={drink id}
    @GET("/api/json/v1/1/lookup.php?i=")
    suspend fun getDrinkById(
        @Query("i") drinkId: String
    ) : DrinksResponse
}