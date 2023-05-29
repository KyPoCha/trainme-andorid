package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.InfoDark
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.PrimaryVariant

@Composable
@OptIn(ExperimentalPagerApi::class)
fun ImageSlider(state: PagerState, images: List<Int>){
    HorizontalPager(
        state = state,
        count = 4,
        modifier = Modifier
            .height(200.dp)
            .fillMaxWidth()
    ) {
            page ->
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.BottomCenter) {
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "",
                    modifier = Modifier
                        .padding(8.dp)
                        .clip(RoundedCornerShape(15.dp))
                        .fillMaxSize(),
                    contentScale = ContentScale.FillWidth,
                )
            }
        }
    }
}

@Composable
fun DotsIndicator(
    totalDots: Int,
    selectedIndex: Int
) {

    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(), horizontalArrangement = Arrangement.Center
    ) {

        items(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = PrimaryVariant)
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color = InfoDark)
                )
            }

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}