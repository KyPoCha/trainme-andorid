package cz.cvut.fit.poliskyr.trainmeapp.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.components.EmailColumn
import cz.cvut.fit.poliskyr.trainmeapp.components.InfoColumn
import cz.cvut.fit.poliskyr.trainmeapp.components.TopBar
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Dark
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.InfoDark
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.Warning
import cz.cvut.fit.poliskyr.trainmeapp.util.Validator
import kotlinx.coroutines.launch

@Composable
fun ContactsScreen(navController: NavController){
    var textName by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val emailSuccess = stringResource(id = R.string.send_email_success)
    val emailFailure = stringResource(id = R.string.send_email_failure)
    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            TopBar(
                title = stringResource(id = R.string.contacts),
                buttonIcon = Icons.Filled.ArrowBack,
                onButtonClicked = { navController.popBackStack() },
                isTrainingScreen = false
            )
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                            EmailColumn()
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = textName,
                                onValueChange = { textName = it },
                                placeholder = {
                                    Text(
                                        modifier = Modifier,
                                        text = stringResource(id = R.string.enter_name),
                                        color = InfoDark,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                textStyle = TextStyle(
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                ),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Warning.copy(alpha = ContentAlpha.medium),
                                    textColor = Dark,
                                    focusedIndicatorColor = Warning
                                )
                            )
                            Spacer(Modifier.height(10.dp))
                            TextField(
                                modifier = Modifier.fillMaxWidth(),
                                value = textEmail,
                                onValueChange = { textEmail = it },
                                placeholder = {
                                    Text(
                                        modifier = Modifier,
                                        text = stringResource(id = R.string.enter_email),
                                        color = InfoDark,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                },
                                textStyle = TextStyle(
                                    fontSize = MaterialTheme.typography.bodyLarge.fontSize
                                ),
                                singleLine = true,
                                colors = TextFieldDefaults.textFieldColors(
                                    cursorColor = Warning.copy(alpha = ContentAlpha.medium),
                                    textColor = Dark,
                                    focusedIndicatorColor = Warning
                                )
                            )
                            Spacer(Modifier.height(18.dp))
                            Button(
                                onClick = {
                                    val validator = Validator()
                                    if(validator.checkEmail(textEmail)){
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                emailSuccess
                                            ) }
                                        textEmail = ""
                                        textName = ""
                                    }
                                    else {
                                        scope.launch {
                                            snackbarHostState.showSnackbar(
                                                emailFailure
                                            )
                                        }
                                    }
                                },
                                shape = RoundedCornerShape(10.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Warning,
                                    contentColor = Dark
                                ),
                                modifier = Modifier.size(height = 50.dp, width = 100.dp),
                                content = { Text(text = stringResource(id = R.string.send)) }
                            )
                    }
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        InfoColumn()
                    }
                }
            }
    }
}
