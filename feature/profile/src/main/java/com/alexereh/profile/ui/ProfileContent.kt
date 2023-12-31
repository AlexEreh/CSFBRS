package com.alexereh.profile.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alexereh.model.PersonData
import com.alexereh.profile.ProfileStore
import com.alexereh.profile.component.ProfileComponent

@Composable
fun ProfileContent(component: ProfileComponent) {
    val state by component.state.collectAsState()
    Scaffold (
        topBar = {
            ProfileTopAppBar(
                onBackAction = component::doBackAction
            )
        }
    ){ scaffoldPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .width(IntrinsicSize.Max)
                .height(IntrinsicSize.Max)
                .padding(10.dp)
        ) {
            Column(
                modifier = Modifier
                    .height(IntrinsicSize.Max)
                    .weight(1f, true)
            ) {
                when (state) {
                    is ProfileStore.State.Error -> {
                        Text((state as ProfileStore.State.Error).message ?: "No message error")
                    }
                    is ProfileStore.State.Idle -> {

                    }
                    is ProfileStore.State.Loading -> {
                        LoadingView(modifier = Modifier.fillMaxSize())
                    }
                    is ProfileStore.State.Success -> {
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
                                val personDataState = remember {
                                    mutableStateOf((state as ProfileStore.State.Success).data)
                                }
                                PersonDataCardContent(
                                    personDataState = personDataState
                                )
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = component::doLogoutAction,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(
                        text = "Выйти из аккаунта",
                        fontSize = 20.sp
                    )
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
    personDataState: State<PersonData>
) {
    val personData by personDataState
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