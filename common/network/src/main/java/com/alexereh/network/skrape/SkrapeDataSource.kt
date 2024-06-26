package com.alexereh.network.skrape

import android.util.Log
import arrow.core.Either
import arrow.core.Option
import arrow.core.left
import arrow.core.none
import arrow.core.right
import com.alexereh.model.Marks
import com.alexereh.model.PersonData
import com.alexereh.model.ScoringType
import com.alexereh.model.StatisticRow
import com.alexereh.network.NetworkDataSource
import com.alexereh.network.util.Endpoint
import it.skrape.core.document
import it.skrape.fetcher.BrowserFetcher
import it.skrape.fetcher.HttpFetcher
import it.skrape.fetcher.Method
import it.skrape.fetcher.Result
import it.skrape.fetcher.response
import it.skrape.fetcher.skrape

class SkrapeDataSource(
    private val baseEndpoint: Endpoint
) : NetworkDataSource {
    override suspend fun getGrades(login: String, password: String): Either<String, List<StatisticRow>> {
        return skrape(BrowserFetcher) {
            request {
                method = Method.POST
                url = baseEndpoint.url
                timeout = 10_000
                followRedirects = true
                body {
                    form {
                        "login" to login
                        "password" to password
                        "button_login" to "Вход"
                    }
                }
            }
            response(fun Result.(): Either<String, List<StatisticRow>> {
                if (responseStatus.code !in 200..399) {
                    Log.d("NETWORK", "Response: ${responseStatus.code} $responseBody")
                    return "Запрос не удался: ${responseStatus.message}".left()
                }
                if (document.allElements.indexOfFirst { it.tagName == "dl" } == -1) {
                    return "Не была найдена таблица".left()
                }

                val gradesRowElementsList = document.findAll("tbody > tr")
                val staticRows = gradesRowElementsList.map {
                    StatisticRow(
                        studyYear = it.children[0].ownText,
                        semesterNumber = it.children[1].ownText.toByte(),
                        courseNumber = it.children[2].ownText.toByte(),
                        disciplineName = it.children[3].ownText,
                        scoringType = ScoringType.getTypeFromString(it.children[4].ownText),
                        tutor = it.children[5].ownText,
                        marks = Marks(
                            firstAttestationScore = it.children[6].ownText.toIntOrNull(),
                            secondAttestationScore = it.children[7].ownText.toIntOrNull(),
                            thirdAttestationScore = it.children[8].ownText.toIntOrNull(),
                            examScore = it.children[11].ownText.toIntOrNull(),
                            additionalScore = it.children[12].ownText.toIntOrNull(),
                            resultScore = it.children[13].ownText.toIntOrNull(),
                            resultString = it.children[14].ownText
                        )
                    )
                }
                return staticRows.right()
            })
        }
    }

    override suspend fun getPerson(login: String, password: String): Either<String, PersonData> {
        return skrape(BrowserFetcher) {
            request {
                method = Method.POST
                url = baseEndpoint.url
                timeout = 10_000
                followRedirects = true
                body {
                    form {
                        "login" to login
                        "password" to password
                        "button_login" to "Вход"
                    }
                }
            }
            response(fun Result.(): Either<String, PersonData> {
                if (responseStatus.code !in 200..399) {
                    Log.d("NETWORK", "Response: ${responseStatus.code} $responseBody")
                    return "Запрос не удался: ${responseStatus.message}".left()
                }
                if (document.allElements.indexOfFirst { it.tagName == "dl" } == -1) {
                    return "Не была найдена таблица".left()
                }
                val primaryElementsList = document.findAll("dl > dd")
                val fullNameSplit = primaryElementsList[0].ownText.split(" ")
                val course = primaryElementsList[1].ownText.toInt()
                val semester = primaryElementsList[2].ownText.toInt()
                val subGroup = primaryElementsList[3]
                    .ownText
                    .drop(1).dropLast(1)
                    .toInt()
                val group = primaryElementsList[3].children.first().ownText.toInt()
                val specialty = primaryElementsList[4].ownText

                return PersonData(
                    firstName = fullNameSplit[1],
                    lastName = fullNameSplit[0],
                    patronymic = fullNameSplit[2],
                    course = course,
                    semester = semester,
                    group = group,
                    subGroup = subGroup,
                    specialty = specialty
                ).right()
            })
        }
    }
}