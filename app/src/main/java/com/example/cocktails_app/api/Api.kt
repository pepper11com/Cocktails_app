package com.example.cocktails_app.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class Api {

    companion object {

        const val BASE_URL = "https://www.thecocktaildb.com"

        //Add /preview to the end of the cocktail image URL
        // /images/media/drink/vrwquq1478252802.jpg/preview (100x100 pixels)
        const val IMAGE_URL = "/images/media/drink/"

        //Ingredient Thumbnails
        //www.thecocktaildb.com/images/ingredients/gin-Small.png
        //(100x100 pixels)
        //www.thecocktaildb.com/images/ingredients/gin-Medium.png
        //(350x350 pixels)
        //www.thecocktaildb.com/images/ingredients/gin.png
        //(700x700 pixels)

        const val INGREDIENT_URL = "https://www.thecocktaildb.com/images/ingredients/"

        const val INGREDIENT_URL_SMALL = "-Small.png"
        const val INGREDIENT_URL_MEDIUM = "-Medium.png"
        const val INGREDIENT_URL_LARGE = ".png"

        const val INGREDIENT_URL_PREVIEW = "/preview"


        val client by lazy { createApi() }

        private fun createApi(): ApiService {
            val client = OkHttpClient.Builder().apply {
                addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                readTimeout(10, TimeUnit.SECONDS)
                writeTimeout(10, TimeUnit.SECONDS)
            }.build()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}