package com.example.cocktails_app.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cocktails_app.viewmodel.CocktailViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.cocktails_app.api.util.Resource
import com.example.cocktails_app.datamodel.Drink
import com.example.cocktails_app.datamodel.DrinksResponse


@Composable
fun SearchCocktailView(
    viewModel: CocktailViewModel = viewModel(),
    navController: NavController
) {
    val searchResults by viewModel.cocktailList.observeAsState(initial = Resource.Empty())
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF242424))
    ) {
        SearchView(
            viewModel = viewModel
        )
        CocktailList(
            cocktailList = searchResults,
            navController = navController,
            viewModel = viewModel
        )
    }
}

@Composable
fun CocktailList(
    cocktailList: Resource<DrinksResponse>,
    navController: NavController,
    viewModel: CocktailViewModel
) {
    when (cocktailList) {
        is Resource.Success -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp, start = 16.dp, end = 16.dp)
            ) {
                items(cocktailList.data?.drinks?.size ?: 0) { index ->
                    val drink = cocktailList.data?.drinks?.get(index)
                    if (drink != null) {
                        CocktailItem(
                            drink = drink,
                            navController = navController,
                            viewModel = viewModel
                        )
                    }
                }
            }
        }
        is Resource.Error -> {
            Text(
                text = cocktailList.message ?: "Something went wrong",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 18.sp
            )
        }
        is Resource.Loading -> {
            CircularProgressIndicator(
                modifier = Modifier
                    .size(48.dp)
            )
        }
        else -> {
            Text(
                text = "No results",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 18.sp
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CocktailItem(
    drink: Drink,
    navController: NavController,
    viewModel: CocktailViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),

        colors = CardDefaults.cardColors(
            containerColor  = MaterialTheme.colorScheme.secondary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
        ),

        onClick = {
            viewModel.searchCocktailById(drink.id)
            navController.navigate("detail_cocktail")
        }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = drink.name,
                color = Color.White,
                fontSize = 18.sp
            )

            drink.thumbnail?.let {
                CocktailDrinkImage(
                    imageString = it
                )
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun SearchView(
    viewModel: CocktailViewModel
) {
    val searchQueryState = rememberSaveable(stateSaver = TextFieldValue.Saver) {
        mutableStateOf(TextFieldValue(""))
    }
    val keyboardController = LocalSoftwareKeyboardController.current

    val customTextSelectionColors = TextSelectionColors(
        handleColor = Color.LightGray,
        backgroundColor = Color.LightGray.copy(alpha = 0.3f)
    )
    CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
        TextField(
            value = searchQueryState.value,
            onValueChange = { value ->
                searchQueryState.value = value

                viewModel.searchCocktailByName(value.text)
            },
            modifier = Modifier
                .fillMaxWidth()
//                .background(Color(0xFFFF0000))
                .statusBarsPadding(),
            textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
            leadingIcon = {
                IconButton(onClick = {
                    //TODO: Your logic here
                    viewModel.searchCocktailByName(searchQueryState.value.text)

                    keyboardController?.hide()
                }) {
                    Icon(
                        Icons.Default.Search,
                        tint = Color.White,
                        contentDescription = "",
                        modifier = Modifier
                            .size(24.dp)
                    )
                }
            },
            trailingIcon = {
                if (searchQueryState.value != TextFieldValue("")) {
                    IconButton(
                        onClick = {
                            searchQueryState.value =
                                TextFieldValue("") // Remove text from TextField when you press the 'X' icon
                        }
                    ) {
                        Icon(
                            Icons.Default.Close,
                            tint = Color.White,
                            contentDescription = "",
                            modifier = Modifier
                                .size(24.dp)
                        )
                    }
                }
            },
            placeholder = {
                Text(
                    text = "Search",
                    color = Color.White
                )
            },
            singleLine = true,
            shape = RectangleShape, // The TextFiled has rounded corners top left and right by default
            colors = TextFieldDefaults.textFieldColors(
                textColor = Color.White,
                cursorColor = Color.White,
                placeholderColor = Color.White,
                containerColor = Color.Transparent,
//                leadingIconColor = Color.White,
//                trailingIconColor = Color.White,
//                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                //copy symbol color
                errorIndicatorColor = Color.Transparent,
            )
        )
    }

    Spacer(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp)
            .height(2.dp)
            .background(Color.White),
    )
}



@Composable
fun CocktailDrinkImage(imageString: String) {
    val painter = rememberAsyncImagePainter(
        ImageRequest.Builder(LocalContext.current)
            .data(
                imageString
            )
            .apply(block = fun ImageRequest.Builder.() {
                memoryCachePolicy(CachePolicy.ENABLED)
            }).build()
    )
    Image(
        painter = painter,
        contentDescription = "Cocktail Drink Image",
        modifier = Modifier
                //make image round
            .clip(CircleShape)
            .size(48.dp),
        alignment = Alignment.Center,
    )
}
