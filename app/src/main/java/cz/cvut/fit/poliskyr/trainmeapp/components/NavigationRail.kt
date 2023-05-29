package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fit.poliskyr.trainmeapp.R

@ExperimentalMaterial3Api
@Composable
fun NavRail(navController: NavController) {
    NavigationRail {
        RailItem(painterId = R.drawable.dashboard, text = "Dashboard", onClick = {})
        RailItem(painterId = R.drawable.group, text = "Trainers", onClick = {})
        RailItem(painterId = R.drawable.add, text = "Add Training", onClick = {})
        RailItem(painterId = R.drawable.list, text = "My Trainings", onClick = {})
        RailItem(painterId = R.drawable.contacts, text = "Contacts", onClick = {})
        RailItem(painterId = R.drawable.logout, text = "Logout", onClick = {})
    }
}

@Composable
fun RailItem(painterId: Int, text: String, onClick: () -> Unit){
    NavigationRailItem(
        selected = true,
        onClick = { onClick() },
        label = { Text(text = text) },
        icon = {
            Icon(
                painter = painterResource(id = painterId),
                contentDescription = text,
                modifier = Modifier.size(30.dp)
            )
        },
        modifier = Modifier.padding(20.dp)
    )
}