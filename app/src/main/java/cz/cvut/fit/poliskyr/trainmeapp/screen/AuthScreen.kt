package cz.cvut.fit.poliskyr.trainmeapp.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ContentAlpha
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import cz.cvut.fit.poliskyr.trainmeapp.R
import cz.cvut.fit.poliskyr.trainmeapp.navigation.Screen
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignInRequest
import cz.cvut.fit.poliskyr.trainmeapp.networking.payload.request.SignUpRequest
import cz.cvut.fit.poliskyr.trainmeapp.presentation.AuthViewModel
import cz.cvut.fit.poliskyr.trainmeapp.ui.theme.*
import cz.cvut.fit.poliskyr.trainmeapp.util.Validator
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(navController: NavController, authViewModel: AuthViewModel) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    Scaffold(snackbarHost = {
        androidx.compose.material.SnackbarHost(
            snackbarHostState
        )
    }) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            ClickableText(
                text = AnnotatedString(stringResource(id = R.string.sign_up_here)),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(20.dp),
                onClick = { openDialog = true },
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily.Default,
                    textDecoration = TextDecoration.Underline,
                    color = PrimaryVariant
                )
            )
        }
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (openDialog) {
                SignUpDialogWindow(
                    onDismiss = {openDialog = false},
                    onPost = { scope.launch {
                        snackbarHostState.showSnackbar(
                            "User was successfully registered"
                        ) }
                    },
                    onErrorEmail = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Email is not correct!"
                            ) }
                    },
                    onErrorPassword = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                "Passwords don't match to each other!"
                            ) }
                    },
                    onErrorEmpty = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                            "All fields must be filled out!"
                            )
                        }
                    },
                    authViewModel = authViewModel
                )
            }

            val username = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }

            Text(
                text = stringResource(id = R.string.login),
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Cursive)
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = stringResource(id = R.string.username)) },
                value = username.value,
                onValueChange = { usernameVal -> username.value = usernameVal },
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                        cursorColor = Warning.copy(alpha = ContentAlpha.medium),
                        textColor = Dark,
                        focusedIndicatorColor = Warning
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            TextField(
                label = { Text(text = stringResource(id = R.string.password)) },
                value = password.value,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = { passwordVal -> password.value = passwordVal },
                colors = androidx.compose.material3.TextFieldDefaults.textFieldColors(
                    cursorColor = Warning.copy(alpha = ContentAlpha.medium),
                    textColor = Dark,
                    focusedIndicatorColor = Warning
                )
            )

            Spacer(modifier = Modifier.height(20.dp))
            Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                Button(
                    onClick = {
                        Log.d("LOGIN INFO", username.value)
                        authViewModel.signIn(
                            signInRequest = SignInRequest(
                                username.value,
                                password.value
                            )
                        )
                        navController.navigate(Screen.MainScreen.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Warning,
                        contentColor = Dark
                    ),
                    shape = RoundedCornerShape(50.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(start = 5.dp, end = 5.dp)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }
    }
}

@Composable
fun SignUpDialogWindow(
    onDismiss: () -> Unit,
    onPost: () -> Job,
    onErrorEmail: () -> Job,
    onErrorPassword: () -> Job,
    onErrorEmpty: () -> Job,
    authViewModel: AuthViewModel
){
    var textUsername by remember { mutableStateOf("") }
    var textEmail by remember { mutableStateOf("") }
    var textFirstName by remember { mutableStateOf("") }
    var textLastName by remember { mutableStateOf("") }
    var textPassword by remember { mutableStateOf("") }
    var textConfirmPassword by remember { mutableStateOf("") }
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var passwordConfirmVisible by rememberSaveable { mutableStateOf(false) }
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shadowElevation = 4.dp
        ) {
            Column(
                modifier = Modifier.verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.fill_form) ,
                    modifier = Modifier.padding(top = 16.dp ,start = 36.dp, end = 36.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge,
                    color =  Dark
                )

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 20.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                    value = textUsername,
                    onValueChange = { textUsername = it },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.enter_username),
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

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp),
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
                Spacer(Modifier.height(10.dp))

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp),
                    value = textFirstName,
                    onValueChange = { textFirstName = it },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.enter_first_name),
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

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp),
                    value = textLastName,
                    onValueChange = { textLastName = it },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.enter_last_name),
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

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp),
                    value = textPassword,
                    onValueChange = { textPassword = it },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.enter_password),
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
                    ),
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)

                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(painter  = image, description)
                        }
                    }
                )
                Spacer(Modifier.height(10.dp))

                androidx.compose.material.TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 36.dp, end = 36.dp, bottom = 8.dp),
                    value = textConfirmPassword,
                    onValueChange = { textConfirmPassword = it },
                    placeholder = {
                        Text(
                            modifier = Modifier,
                            text = stringResource(id = R.string.enter_confirm_password),
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
                    ),
                    visualTransformation = if (passwordConfirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordConfirmVisible)
                            painterResource(id = R.drawable.visibility)
                        else painterResource(id = R.drawable.visibility_off)

                        // Please provide localized description for accessibility services
                        val description = if (passwordConfirmVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordConfirmVisible = !passwordConfirmVisible}){
                            Icon(painter  = image, description)
                        }
                    }
                )
                Spacer(Modifier.height(10.dp))

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 8.dp),
                    shape = RoundedCornerShape(50.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Warning),
                    onClick = {
                        val validator = Validator()
                        if(!validator.checkForm(
                                textUsername,
                                textEmail,
                                textFirstName,
                                textLastName,
                                textPassword,
                                textConfirmPassword
                        )){
                            onErrorEmpty()
                        }
                        if(!validator.checkEmail(textEmail)){
                            onErrorEmail()
                        }
                        if(!validator.checkPasswords(textPassword, textConfirmPassword))
                        {
                            onErrorPassword()
                        }
                        authViewModel.signUp(
                            SignUpRequest(
                                textEmail,
                                textUsername,
                                textFirstName,
                                textLastName,
                                textPassword,
                                textConfirmPassword)
                        )
                        onPost()
                        onDismiss()
                    }) {
                    Text(
                        text = stringResource(id = R.string.sign_up_now),
                    )
                }

                TextButton(
                    onClick = {
                        onDismiss()
                    }) {
                    Text(
                        text = stringResource(id = R.string.back_to_sign_in),
                        color = Dark,
                        style = TextStyle(
                            fontSize = 14.sp
                        )
                    )
                }
            }
        }
    }
}