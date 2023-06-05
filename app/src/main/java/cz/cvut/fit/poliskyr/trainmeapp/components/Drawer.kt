package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Screen
import cz.cvut.fit.poliskyr.trainmeapp.presentation.UserViewModel

private val screens = listOf(
    Screen.MainScreen,
    Screen.TrainersScreen,
    Screen.TrainingsScreen,
    Screen.ContactsScreen
)

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    userViewModel: UserViewModel,
    onDestinationClicked: (route: String) -> Unit
) {
    val user by userViewModel.user.collectAsState()
    Column(
        modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(start = 24.dp, top = 24.dp),
    ) {
        HeaderBar(username = user.username)
        screens.forEach { screen ->
            Spacer(Modifier.height(48.dp))
            Row(
                modifier = Modifier
                    .width(300.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onDestinationClicked(screen.route)
                    }
            ){
                SideNavIcon(screen = screen)
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        LogOutFooter {onDestinationClicked(Screen.AuthScreen.route) }
    }
}