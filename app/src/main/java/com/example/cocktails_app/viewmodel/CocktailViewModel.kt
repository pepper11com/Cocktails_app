package com.example.cocktails_app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.cocktails_app.api.util.Resource
import com.example.cocktails_app.datamodel.Drink
import com.example.cocktails_app.datamodel.DrinksResponse
import com.example.cocktails_app.repository.CocktailRepository

import kotlinx.coroutines.launch

class CocktailViewModel(application: Application) : AndroidViewModel(application) {

    private val cocktailRepository = CocktailRepository()

    var selectedCocktail: Drink? = null
        private set

    val cocktailList: MutableLiveData<Resource<DrinksResponse>>
        get() = _cocktailListResource
    val cocktailById: MutableLiveData<Resource<DrinksResponse>>
        get() = _cocktailByIdResource

    private val _cocktailListResource: MutableLiveData<Resource<DrinksResponse>> = MutableLiveData(Resource.Empty())
    private val _cocktailByIdResource: MutableLiveData<Resource<DrinksResponse>> = MutableLiveData(Resource.Empty())

    fun searchCocktailByName(cocktailName: String) {
        _cocktailListResource.value = Resource.Loading()

        viewModelScope.launch {
            _cocktailListResource.value = cocktailRepository.searchCocktailByName(cocktailName)
        }
    }

    fun searchCocktailById(cocktailId: String) {
        _cocktailByIdResource.value = Resource.Loading()

        viewModelScope.launch {
            _cocktailByIdResource.value = cocktailRepository.searchCocktailById(cocktailId)
        }
    }

    fun setSelectedCocktail(cocktail: Drink) {
        selectedCocktail = cocktail
    }


}