package dev.xget.freetogame.presentation.games.home

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dev.xget.freetogame.R
import dev.xget.freetogame.domain.model.game.FreeGame
import dev.xget.freetogame.presentation.games.home.components.GameItemCard
import dev.xget.freetogame.presentation.games.home.components.GamesFilterBottomSheet
import dev.xget.freetogame.presentation.utils.ScreensRoutes.DETAILS_SCREEN
import dev.xget.freetogame.presentation.utils.ScreensRoutes.HOME_SCREEN
import dev.xget.freetogame.ui.theme.LightBlue
import dev.xget.freetogame.ui.theme.NormalBlue

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel = hiltViewModel(), navController: NavHostController) {

    // Lifecycle aware composable
    val state = viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    HomeScreenContent(
        searchQuery = state.value.nameQuery,
        onSearchQueryChange = viewModel::onSearchQueryChange,
        freeGames = state.value.games,
        onGameClicked = {
            navController.navigate(
                "$DETAILS_SCREEN/{freeGameId}"
                    .replace(
                        oldValue = "{freeGameId}",
                        newValue = "${it.id}"
                    )
            ){
            }
        },
        onFilterByCategory = viewModel::onFilterByCategory,
        onFilterByOrder = viewModel::onFilterByOrder,
        onFilterByPlatform = viewModel::onFilterByPlatform,
        resetFilters = viewModel::resetFilters,
        platformList = state.value.platformList,
        orderByList = state.value.orderByList,
        categoryList = state.value.categoryList,
        selectedPlatform = state.value.filterPlatform,
        selectedCategory = state.value.filterCategory,
        selectedOrderBy = state.value.filterOrderBy
    )

    if (!state.value.isLoading && !state.value.error.isNullOrEmpty()) {
        Log.d("HomeScreenContent", "state.value.error: ${state.value.error}")
        Toast.makeText(context, state.value.error, Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenContent(
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    onFilterByCategory: (String) -> Unit,
    onFilterByOrder: (String) -> Unit,
    onFilterByPlatform: (String) -> Unit,
    freeGames: List<FreeGame>,
    onGameClicked: (FreeGame) -> Unit,
    resetFilters: () -> Unit,
    platformList: Map<String, String> = mapOf(),
    categoryList: List<String> = emptyList(),
    orderByList: Map<String, String> = mapOf(),
    selectedPlatform: String = "",
    selectedCategory: String = "",
    selectedOrderBy: String = "",
    isLoading: Boolean = false,
) {

    val showFilterBottomSheet = remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(
        containerColor = Color.White
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .padding(top = 50.dp)
        ) {
            Column {
                Text(
                    text = "Free Games App",
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = onSearchQueryChange,
                    modifier = Modifier
                        .fillMaxWidth()
                        .onKeyEvent { event ->
                            if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                                focusManager.clearFocus()
                                true
                            } else {
                                false

                            }
                        },
                    trailingIcon = {
                        if (searchQuery.isEmpty()) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search",
                                tint = Color.Gray
                            )

                        } else {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Search",
                                tint = Color.DarkGray,
                                modifier = Modifier.clickable {
                                    onSearchQueryChange("")
                                }
                            )
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedBorderColor = Color.LightGray,
                        focusedBorderColor = NormalBlue
                    ),
                    placeholder = {
                        Text(text = "Buscar Titulo de Juego")
                    },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = {
                        showFilterBottomSheet.value = true
                    }, border = BorderStroke(
                        1.dp,
                        Color.LightGray
                    ), contentPadding = PaddingValues(horizontal = 15.dp, vertical = 8.dp)
                ) {
                    Row(
                        modifier = Modifier.width(82.dp),
                        horizontalArrangement = Arrangement.SpaceAround,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_filter_list_24),
                            contentDescription = "Search",
                            tint = NormalBlue
                        )

                        Text(
                            text = "Filtros",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Medium,
                            color = Color.DarkGray
                        )
                    }

                }

                if (isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(color = NormalBlue)
                    }
                }

                if (freeGames.isEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        text = "No hay juegos disponibles con estos filtros.",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }

                LazyColumn(
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.padding(top = 20.dp)
                ) {
                    items(freeGames) { freeGame ->
                        GameItemCard(
                            freeGame = freeGame,
                            onClick = { onGameClicked(freeGame) }
                        )
                    }
                }
            }
        }

        GamesFilterBottomSheet(
            showBottomSheet = showFilterBottomSheet.value,
            onDismiss = { showFilterBottomSheet.value = false },
            onApplyFilters = { showFilterBottomSheet.value = false },
            onResetFilters = resetFilters,
            platformList = platformList,
            categoryList = categoryList,
            orderByList = orderByList,
            selectedPlatform = selectedPlatform,
            selectedCategory = selectedCategory,
            selectedOrderBy = selectedOrderBy,
            onFilterByCategory = onFilterByCategory,
            onFilterByOrder = onFilterByOrder,
            onFilterByPlatform = onFilterByPlatform
        )
    }

    BackHandler {
        showFilterBottomSheet.value = false
        focusManager.clearFocus()
    }
}


@Preview
@Composable
private fun HomeScreenPreview() {

    val freeGames = listOf(
        FreeGame(
            id = 582,
            title = "Tarisland",
            isFavorite = false,
            thumbnail = "https://www.freetogame.com/g/582/thumbnail.jpg",
            shortDescription = "A cross-platform MMORPG developed by Level Infinite and Published by Tencent.",
            description = "",
            gameUrl = "https://www.freetogame.com/open/tarisland",
            genre = "MMORPG",
            platform = "PC (Windows)",
            publisher = "Tencent",
            developer = "Level Infinite",
            releaseDate = "2024-06-22",
            freeToGameProfileUrl = "https://www.freetogame.com/tarisland",
            screenshots = emptyList()
        ),
        FreeGame(
            id = 540,
            title = "Overwatch 2",
            isFavorite = true,
            thumbnail = "https://www.freetogame.com/g/540/thumbnail.jpg",
            shortDescription = "A hero-focused first-person team shooter from Blizzard Entertainment.",
            description = "",
            gameUrl = "https://www.freetogame.com/open/overwatch-2",
            genre = "Shooter",
            platform = "PC (Windows)",
            publisher = "Activision Blizzard",
            developer = "Blizzard Entertainment",
            releaseDate = "2022-10-04",
            freeToGameProfileUrl = "https://www.freetogame.com/overwatch-2",
            screenshots = emptyList()
        ),
        FreeGame(
            id = 516,
            title = "PUBG: BATTLEGROUNDS",
            isFavorite = false,
            thumbnail = "https://www.freetogame.com/g/516/thumbnail.jpg",
            shortDescription = "Get into the action in one of the longest running battle royale games PUBG Battlegrounds.",
            description = "",
            gameUrl = "https://www.freetogame.com/open/pubg",
            genre = "Shooter",
            platform = "PC (Windows)",
            publisher = "KRAFTON, Inc.",
            developer = "KRAFTON, Inc.",
            releaseDate = "2022-01-12",
            freeToGameProfileUrl = "https://www.freetogame.com/pubg",
            screenshots = emptyList()
        ),
        FreeGame(
            id = 508,
            title = "Enlisted",
            isFavorite = false,
            thumbnail = "https://www.freetogame.com/g/508/thumbnail.jpg",
            shortDescription = "Get ready to command your own World War II military squad in Gaijin and Darkflow Software’s MMO squad-based shooter Enlisted.",
            description = "",
            gameUrl = "https://www.freetogame.com/open/enlisted",
            genre = "Shooter",
            platform = "PC (Windows)",
            publisher = "Gaijin Entertainment",
            developer = "Darkflow Software",
            releaseDate = "2021-04-08",
            freeToGameProfileUrl = "https://www.freetogame.com/enlisted",
            screenshots = emptyList(),
            status = ""
        ),
        FreeGame(
            id = 345,
            title = "Forge of Empires",
            isFavorite = false,
            thumbnail = "https://www.freetogame.com/g/345/thumbnail.jpg",
            shortDescription = "A free to play 2D browser-based online strategy game, become the leader and raise your city.",
            description = "",
            gameUrl = "https://www.freetogame.com/open/forge-of-empires",
            genre = "Strategy",
            platform = "Web Browser",
            publisher = "InnoGames",
            developer = "InnoGames",
            releaseDate = "2012-04-17",
            freeToGameProfileUrl = "https://www.freetogame.com/forge-of-empires",
            screenshots = emptyList(),
            status = "Active"
        ),
        // Continue creating FreeGame objects for the remaining data...
    )


    HomeScreenContent(
        freeGames = freeGames,
        onGameClicked = {},
        onSearchQueryChange = {},
        searchQuery = "",
        onFilterByCategory = {},
        onFilterByOrder = {},
        onFilterByPlatform = {},
        resetFilters = {}
    )
}

