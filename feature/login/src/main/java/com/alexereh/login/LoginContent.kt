package com.alexereh.login

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.alexereh.ui.theme.CSFBRSTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun LoginContent(component: LoginComponent) {
    val loginText by component.loginText.collectAsStateWithLifecycle()
    val passwordText by component.passwordText.collectAsStateWithLifecycle()
    LoginContent(
        modifier = Modifier.fillMaxSize(),
        login = loginText,
        password = passwordText,
        updateLoginText = component::updateLoginText,
        updatePasswordText = component::updatePasswordText,
        doLogin = component::doLogin
    )
}

@Composable
fun LoginContent(
    modifier: Modifier,
    login: String,
    password: String,
    updateLoginText: (String) -> Unit,
    updatePasswordText: (String) -> Unit,
    doLogin: () -> Unit
) {
    val showPassword = remember { mutableStateOf(false) }
    // Remember a SystemUiController
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()
    val surfaceColor = MaterialTheme.colorScheme.surface
    DisposableEffect(systemUiController, useDarkIcons) {
        // Update all of the system bar colors to be transparent, and use
        // dark icons if we're in light theme
        systemUiController.setSystemBarsColor(
            color = surfaceColor,
            darkIcons = useDarkIcons
        )

        // setStatusBarColor() and setNavigationBarColor() also exist

        onDispose {}
    }
    Surface(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.wrapContentSize().width(IntrinsicSize.Min),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = login,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next
                ),
                onValueChange = { updateLoginText(it) },
                label = {
                    Text(text = "Логин")
                },
                shape = CircleShape
            )
            OutlinedTextField(
                value = password,
                onValueChange = { updatePasswordText(it) },
                label = {
                    Text("Пароль")
                },
                visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            showPassword.value = !showPassword.value
                        }
                    ) {
                        Icon(
                            imageVector = if (showPassword.value) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = if (showPassword.value) "Show Password" else "Hide Password"
                        )
                    }
                },
                keyboardActions = KeyboardActions(),
                shape = CircleShape
            )
            Button(
                onClick = doLogin,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Войти",
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun LoginContentPreview() {
    CSFBRSTheme {
        LoginContent(component = FakeLoginComponent())
    }
}