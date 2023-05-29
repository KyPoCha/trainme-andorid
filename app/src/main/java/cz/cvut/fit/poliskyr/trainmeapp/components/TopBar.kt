package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit, isTrainingScreen: Boolean) {
    TopAppBar(
        title = {
            Text(
                text = title
            )
        },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Color.Transparent),
    )
}