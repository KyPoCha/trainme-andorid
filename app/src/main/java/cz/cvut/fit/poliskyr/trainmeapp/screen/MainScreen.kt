package cz.cvut.fit.poliskyr.trainmeapp.screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.components.*
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.InfoDark
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.PrimaryVariant
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun MainScreen(navController: NavController, openDrawer: () -> Unit) {
    val listOfImages = listOf(
        R.drawable._gym2,
        R.drawable._gym3,
        R.drawable._pool1,
        R.drawable._pool2
    )
    Column(modifier = Modifier
        .fillMaxSize()
    ) {
        TopBar(
            title = stringResource(id = R.string.home),
            buttonIcon = Icons.Filled.Menu,
            onButtonClicked = { openDrawer() },
            isTrainingScreen = false
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(){
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable._gym1),
                        contentDescription = "gym",
                        modifier = Modifier.clip(RoundedCornerShape(5.dp))
                    )
                    InfoHome()
                    val state = rememberPagerState(0)
                    ImageSlider(state = state, images = listOfImages)
                    Spacer(Modifier.height(5.dp))
                    DotsIndicator(
                        totalDots = 4,
                        selectedIndex = state.currentPage
                    )
                    InfoTitles()
                    InfoTeam()
                }
            }
        }
    }
}