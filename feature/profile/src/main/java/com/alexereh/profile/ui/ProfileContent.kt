package com.alexereh.profile.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alexereh.model.PersonData
import com.alexereh.profile.component.FakeProfileComponent
import com.alexereh.profile.component.ProfileComponent
import com.alexereh.ui.theme.CSFBRSTheme
import com.alexereh.util.Result
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun ProfileContent(component: ProfileComponent) {
    val personData = component.personData.collectAsState()
    ProfileContent(
        modifier = Modifier.fillMaxSize(),
        backAction = component::doBackAction,
        logoutAction = component::doLogoutAction,
        result = personData.value
    )
}

@Composable
fun ProfileContent(
    result: Result<PersonData>,
    modifier: Modifier = Modifier,
    backAction: () -> Unit,
    logoutAction: () -> Unit
) {
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

    Scaffold (
        topBar = {
            ProfileTopAppBar {
                backAction()
            }
        }
    ){ scaffoldPadding ->
        Surface(
            modifier = modifier
                .padding(scaffoldPadding)
                .fillMaxSize()
        ) {

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .width(IntrinsicSize.Max)
                    .height(IntrinsicSize.Max)
                    .padding(10.dp)
            ) {
                Column(
                    modifier = Modifier.height(IntrinsicSize.Max).weight(1f, true)
                ) {
                    when (result) {
                        is Result.Loading -> {
                            LoadingView(modifier = modifier.fillMaxSize())
                        }
                        is Result.Success -> {
                            OutlinedCard(
                                modifier = Modifier
                                    //.wrapContentSize(Alignment.Center)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    modifier = Modifier
                                        .width(IntrinsicSize.Max)
                                        .height(IntrinsicSize.Max)
                                ) {
                                    PersonDataCardContent(personData = result.data)
                                }
                            }
                        }
                        is Result.NotLoading -> {

                        }
                        is Result.Error -> {
                            Text(result.exception?.localizedMessage ?: "No message error")
                        }
                    }
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = logoutAction,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Выйти из аккаунта"
                        )
                    }
                }

            }
        }
    }
}

@Composable
fun LoadingView(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun PersonDataCardContent(
    modifier: Modifier = Modifier,
    personData: PersonData
) {
    Column(
        modifier = modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.Start
    ) {
        TextInPersonDataCard(
            text = "ФИО: ${personData.lastName} ${personData.firstName} ${personData.patronymic}"
        )
        TextInPersonDataCard(
            text = "Курс: ${personData.course}"
        )
        TextInPersonDataCard(
            text = "Семестр: ${personData.semester}"
        )
        TextInPersonDataCard(
            text = "Группа: ${personData.group} (${personData.subGroup})"
        )
        TextInPersonDataCard(
            text = "Специальность: ${personData.specialty}"
        )
    }
}

@Composable
fun TextInPersonDataCard(
    text: String
) {
    Text(
        fontWeight = FontWeight.Bold,
        text = text
    )
}

@Preview
@Composable
fun ProfileContentPreview() {
    CSFBRSTheme {
        ProfileContent(component = FakeProfileComponent())
    }
}