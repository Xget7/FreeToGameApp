package dev.xget.freetogame.presentation.games.details

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import dev.xget.freetogame.domain.model.game.FreeGame
import dev.xget.freetogame.domain.model.game.GameScreenshot
import dev.xget.freetogame.presentation.games.ImageGamePager

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GameDetailsScreen(
    viewModel: GameDetailsViewModel = hiltViewModel(),
    nav: NavHostController,
    modifier: Modifier = Modifier
) {

    val state = viewModel.state.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text(text = "Detalles del juego") },
                navigationIcon = {
                    IconButton(onClick = {
                        nav.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White,
                    scrolledContainerColor = Color.White,
                    titleContentColor = Color.Black,
                    navigationIconContentColor = Color.Black
                ),
            )
        },
        containerColor = Color.White,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { paddingValues ->
        GameDetailsScreenContent(
            freeGame = state.value.freeGame,
            modifier = Modifier.padding(paddingValues),
            saveFavorite = viewModel::saveFavoriteGame,
        )
    }
    LaunchedEffect(key1 = state.value.error, block = {
        if (state.value.error != null && !state.value.isLoading) {
            snackbarHostState.showSnackbar(
                message = state.value.error!!,
                actionLabel = "Borrar"
            )
        }
    })
    BackHandler {
        nav.popBackStack()
    }
}


@Composable
fun GameDetailsScreenContent(
    freeGame: FreeGame?,
    saveFavorite: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val uriHandler = LocalUriHandler.current
    if (freeGame != null) {
        Column(
            modifier = Modifier.padding(top = 85.dp)
        ) {
            ImageGamePager(
                urls = listOf(freeGame.thumbnail) + freeGame.screenshots.map { it.url },
                modifier = Modifier.padding(top = 5.dp)
            )


        }
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .padding(top = 230.dp)
                .verticalScroll(rememberScrollState())
                ,
            verticalArrangement = Arrangement.Top
        ) {
            // Title
            Text(
                text = freeGame.title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Developer and Publisher Info
            Text(
                text = "Desarrollado por ${freeGame.developer} and published by ${freeGame.publisher} in ${freeGame.releaseDate}",
                fontSize = 15.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(20.dp))

            // Description
            if (freeGame.description?.isNotBlank() == true) {
                Text(
                    text = freeGame.description,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray
                )
                Spacer(modifier = Modifier.height(10.dp))
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

            // Genre
            if (freeGame.genre?.isNotBlank() == true) {
                Row {
                    Text(
                        text = "Genero",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(80.dp))
                    Text(
                        text = freeGame.genre,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
            HorizontalDivider(modifier = Modifier.padding(vertical = 20.dp))

            if (freeGame.platform?.isNotBlank() == true) {
                Row {
                    Text(
                        text = "Plataforma",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.width(54.dp))
                    Text(
                        text = freeGame.platform,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.DarkGray
                    )
                }
            }
            Spacer(modifier = Modifier.height(40.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        uriHandler.openUri(freeGame.gameUrl ?: "www.freetogame.com")
                    }, colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF359EFF)
                    )
                ) {
                    Text(text = "Jugar Ahora", fontSize = 16.sp, fontWeight = FontWeight.Medium)
                }
                //favorite icon button red
                IconButton(onClick = saveFavorite, modifier = Modifier.size(50.dp)) {
                    Card(
                        shape = CircleShape,
                        modifier = Modifier.size(50.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = BorderStroke(1.dp, Color.LightGray),
                        elevation = CardDefaults.elevatedCardElevation(4.dp)
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            if (freeGame.isFavorite) {
                                Icon(
                                    imageVector = Icons.Default.Favorite,
                                    contentDescription = "Favorite",
                                    tint = Color.Red,
                                    modifier = Modifier.size(30.dp)
                                )
                            }else{
                                Icon(
                                    imageVector = Icons.Default.FavoriteBorder,
                                    contentDescription = "Favorite",
                                    tint = Color.Gray,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                    }

                }

            }
            
            Spacer(modifier = Modifier.height(30.dp))

        }

    }
}

@Preview
@Composable
private fun GameDetailsScreenPreview() {
    val freeGame = FreeGame(
        id = 582,
        title = "Tarisland",
        isFavorite = false,
        thumbnail = "https://www.freetogame.com/g/582/thumbnail.jpg",
        shortDescription = "A cross-platform MMORPG developed by Level Infinite and Published by Tencent.",
        description = "Tarisland offers an expansive cross-platform MMORPG experience, complete with dynamic gameplay, stunning visuals, and a rich storyline.",
        gameUrl = "https://www.freetogame.com/open/tarisland",
        genre = "MMORPG",
        platform = "PC (Windows)",
        publisher = "Tencent",
        developer = "Level Infinite",
        releaseDate = "2024-06-22",
        freeToGameProfileUrl = "https://www.freetogame.com/tarisland",
        screenshots = listOf(
            GameScreenshot(
                id = 1448,
                url = "https://www.freetogame.com/g/582/tarisland-1.jpg"
            ),
            GameScreenshot(
                id = 1449,
                url = "https://www.freetogame.com/g/582/tarisland-2.jpg"
            ),
            GameScreenshot(
                id = 1450,
                url = "https://www.freetogame.com/g/582/tarisland-3.jpg"
            )
        )
    )
    GameDetailsScreenContent(
        freeGame = freeGame,
        saveFavorite = {},
    )
}
