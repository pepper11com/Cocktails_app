package com.example.cocktails_app.repository

import android.util.Log
import com.example.cocktails_app.api.Api
import com.example.cocktails_app.api.ApiService
import com.example.cocktails_app.api.util.Resource
import com.example.cocktails_app.datamodel.DrinksResponse
import kotlinx.coroutines.withTimeout

class CocktailRepository {

    private val cocktailApi: ApiService = Api.client

    suspend fun searchCocktailByName(cocktailName: String): Resource<DrinksResponse> {
        val response = try {
            withTimeout(5_000) {
                cocktailApi.getDrinksByName(cocktailName)
            }
        } catch(e: Exception) {
            Log.e("CocktailRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }

    //todo int or string?
    suspend fun searchCocktailById(cocktailId: String): Resource<DrinksResponse> {
        val response = try {
            withTimeout(5_000) {
                cocktailApi.getDrinkById(cocktailId)
            }
        } catch(e: Exception) {
            Log.e("CocktailRepository", e.message ?: "No exception message available")
            return Resource.Error("An unknown error occurred")
        }

        return Resource.Success(response)
    }
}