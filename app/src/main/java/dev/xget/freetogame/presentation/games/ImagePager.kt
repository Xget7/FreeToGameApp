package dev.xget.freetogame.presentation.games

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import dev.xget.freetogame.ui.theme.NormalBlue

@Composable
fun ImageGamePager(urls: List<String?>, modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(0){
        urls.size
    }

    HorizontalPager(
        state = pagerState,
        modifier = modifier
    ) { page ->
        SubcomposeAsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(urls?.get(page))
                .crossfade(true)
                .build(),
            loading = {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator(
                        color = NormalBlue,
                        modifier = Modifier.padding(48.dp)
                    )
                }
            },
            contentDescription = "Game Photos",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.height(190.dp).fillMaxWidth(),

        )
    }
    Spacer(modifier = Modifier.size(8.dp))
    HorizontalTabs(
        items = urls,
        pagerState = pagerState
    )
}

@Composable
private fun HorizontalTabs(
    items: List<Any?>?,
    pagerState: PagerState,
) {
    val dotRadius = 4.dp
    val dotSpacing = 8.dp

    Box(
        modifier = Modifier
            .height(dotRadius * 2)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center),
            horizontalArrangement = Arrangement.spacedBy(dotSpacing),
        ) {
            items?.forEachIndexed { index, _ ->
                Box(
                    modifier = Modifier
                        .size(dotRadius * 2)
                        .clip(CircleShape)
                        .background(
                            if (pagerState.currentPage == index) NormalBlue else Color.LightGray
                        ),
                )
            }
        }
    }
}