package cz.cvut.fit.poliskyr.trainmeapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import cz.cvut.fit.poliskyr.trainmeapp.model.Trainer
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.PrimaryVariant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropDownMenu(username: MutableState<String>, trainers: List<Trainer>){

    var mExpanded by remember { mutableStateOf(false) }

    val mTrainers = trainers

    val mSelectedText by remember { mutableStateOf("") }

    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }

    // Up Icon when expanded and down icon when collapsed
    val icon = if (mExpanded)
        Icons.Filled.KeyboardArrowUp
    else
        Icons.Filled.KeyboardArrowDown

    Column(
        modifier = Modifier.clip(
            RoundedCornerShape(16.dp)
        )
    ) {
        OutlinedTextField(
            value = username.value,
            readOnly = true,
            onValueChange = { username.value = mSelectedText },
            modifier = Modifier
                .width(140.dp)
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                },
            placeholder = { Text(text = "Trainer") },
            trailingIcon = {
                Icon(
                    icon,
                    "Drop down icon",
                    Modifier.clickable { mExpanded = !mExpanded },
                    Color.White
                )

            },
            colors = TextFieldDefaults.textFieldColors(
                containerColor = PrimaryVariant,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                cursorColor = Color.White,
                placeholderColor = Color.White,
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color.White,
                textColor = Color.White
            )
        )
        DropdownMenu(
            expanded = mExpanded,
            onDismissRequest = { mExpanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})
                .background(color = Color.White)
        ) {
            mTrainers.forEach { trainer ->
                DropdownMenuItem(
                    onClick = {
                        username.value = trainer.username
                        mExpanded = false
                    },
                    text = { Text(text = trainer.username) }
                )
            }
        }
    }
}