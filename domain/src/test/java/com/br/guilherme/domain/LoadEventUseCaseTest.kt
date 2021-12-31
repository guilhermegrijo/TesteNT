package com.br.guilherme.domain

import app.cash.turbine.test
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.eventDetail.EventDetailRepository
import com.br.guilherme.domain.eventDetail.usecase.LoadEventUseCase
import com.br.guilherme.domain.model.EventModel
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.stub
import kotlin.time.ExperimentalTime


@RunWith(JUnit4::class)
class LoadEventUseCaseTest {


    private val repo: EventDetailRepository = mock()

    data class Event(
        override val people: List<Any?>,
        override var date: Long,
        override val description: String,
        override val image: String,
        override val longitude: Double,
        override val latitude: Double,
        override val price: Double,
        override val title: String,
        override val id: String

    ) : EventModel

    @ExperimentalTime
    @Test
    fun `should return event`() = runBlocking {


       val event = Event(
                people = emptyList(),
                date = 1534784400000,
                description = "O Patas Dadas estará na Redenção, nesse domingo, com cães para adoção e produtos à venda!",
                image = "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png",
                longitude = -51.2146267,
                latitude = -30.0392981,
                price = 29.99,
                title = "Feira de adoção de animais na Redenção",
                id = "1"
            )

        repo.stub {
            onBlocking { getEventDetail("1") } doReturn flowOf(DataResult.OnSuccess(event))
        }

        val expectedPrice = 29.99


        val flow: Flow<DataResult<EventModel>> = LoadEventUseCase(repo).invoke("1")
        flow.test {

            assertThat((awaitItem() as DataResult.OnSuccess).response.price).isEqualTo(expectedPrice)
            awaitComplete()
        }
    }

}