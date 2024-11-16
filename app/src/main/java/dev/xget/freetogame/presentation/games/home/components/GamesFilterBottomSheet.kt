package dev.xget.freetogame.presentation.games.home.components

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.xget.freetogame.data.remote.utils.GameApiConstants
import dev.xget.freetogame.data.remote.utils.GameApiConstants.GamePlatforms
import dev.xget.freetogame.ui.theme.Blue80
import dev.xget.freetogame.ui.theme.NormalBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GamesFilterBottomSheet(
    modifier: Modifier = Modifier,
    showBottomSheet: Boolean = false,
    onDismiss: () -> Unit = {},
    sheetState: SheetState = rememberStandardBottomSheetState(initialValue = SheetValue.Expanded, skipHiddenState =false),
    platformList: Map<String, String> = mapOf(),
    selectedPlatform: String = "",
    categoryList: List<String> = emptyList(),
    selectedCategory: String = "",
    orderByList: Map<String, String> = mapOf(),
    selectedOrderBy: String = "",
    onApplyFilters: () -> Unit = {},
    onResetFilters: () -> Unit = {},
    onFilterByPlatform: (String) -> Unit = {},
    onFilterByCategory: (String) -> Unit = {},
    onFilterByOrder: (String) -> Unit = {}
) {

    LaunchedEffect(Unit,showBottomSheet) {
        snapshotFlow { sheetState.currentValue }
            .collect {
                Log.d(
                    "GamesFilterBottomSheet",
                    "LaunchedEffect sheetState: ${sheetState.currentValue} showBottomSheet: $showBottomSheet"
                )
                if (sheetState.currentValue == SheetValue.PartiallyExpanded || sheetState.currentValue == SheetValue.Hidden) {
                    if (showBottomSheet){
                        sheetState.hide()
                        onDismiss()
                    }else {
                        sheetState.expand()
                    }

                }
            }
    }

    if (showBottomSheet) {
        BottomSheetScaffold(
            content = {
            },
            scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState),
            sheetContainerColor = Color.White,
            sheetContent = {
                Box(modifier = Modifier.fillMaxHeight(0.69f)) {
                    // Sheet content
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 10.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = "Filtros y Categorias",
                                textAlign = TextAlign.Center,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier.fillMaxWidth()
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .offset(y = (-15).dp),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = { /*TODO*/ }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "Close",
                                        tint = Color.DarkGray,
                                        modifier = Modifier
                                            .padding(end = 10.dp)
                                            .clickable {
                                                onDismiss()
                                            }
                                            .size(35.dp),
                                    )
                                }

                            }
                        }

                        Text(
                            text = "Plataforma",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 4.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 4.dp)
                        ) {
                            items(platformList.keys.toList()) { platform ->
                                FilterChip(
                                    onClick = {
                                        onFilterByPlatform(platform)

                                    },
                                    label = {
                                        Text(text = platformList[platform].orEmpty())
                                    },
                                    selected = platform == selectedPlatform,
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Blue80
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                        Text(
                            text = "Ordenar por",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(2),
                            modifier = Modifier.height(80.dp),
                        ) {
                            items(orderByList.keys.toList()) { order ->
                                FilterChip(
                                    onClick = {
                                        onFilterByOrder(order)
                                    },
                                    label = {
                                        Text(text = orderByList[order].orEmpty())
                                    },
                                    selected = order == selectedOrderBy,
                                    modifier = Modifier.padding(4.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Blue80
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))


                        Text(
                            text = "Categorias",
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )

                        LazyHorizontalGrid(
                            rows = GridCells.Fixed(3),
                            modifier = Modifier.height(120.dp),
                        ) {
                            items(categoryList) { category ->
                                FilterChip(
                                    onClick = {
                                        onFilterByCategory(category)
                                    },
                                    label = {
                                        Text(text = category)
                                    },
                                    selected = category == selectedCategory,
                                    modifier = Modifier.padding(4.dp),
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = Blue80
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                            }

                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            OutlinedButton(
                                onClick = onResetFilters,
                                modifier = Modifier,
                                border = BorderStroke(
                                    1.dp,
                                    Color.LightGray
                                ),
                            ) {
                                //Reset filters
                                Text(text = "Borrar filtros", color = Color.Black)
                            }
                            Button(
                                onClick = onApplyFilters,
                                colors = ButtonDefaults.buttonColors(containerColor = NormalBlue)
                            ) {
                                Text(text = "Aplicar filtros", color = Color.White)
                            }
                        }
                    }
                }
            },

            )
    }


}

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
private fun PreviewFilterBottomSheet() {
    Box(Modifier.fillMaxSize()) {
        GamesFilterBottomSheet(
            platformList = GamePlatforms,
            selectedPlatform = GamePlatforms.keys.first(),
            categoryList = GameApiConstants.GameTags,
            orderByList = GameApiConstants.GameSortOptions,
            selectedCategory = GameApiConstants.GameTags.first(),
            selectedOrderBy = GameApiConstants.GameSortOptions.keys.first()
        )
    }
}