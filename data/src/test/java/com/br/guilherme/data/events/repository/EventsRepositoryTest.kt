package com.br.guilherme.data.events.repository

import app.cash.turbine.test
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.data.events.remote.datasource.EventService
import com.br.guilherme.data.events.remote.model.Event
import com.br.guilherme.domain.events.EventsRepository
import com.br.guilherme.domain.model.EventModel
import com.google.gson.Gson
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.mockwebserver.MockResponse
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import java.util.concurrent.TimeUnit
import kotlin.time.Duration
import kotlin.time.ExperimentalTime


@InternalCoroutinesApi
@RunWith(JUnit4::class)
class EventsRepositoryTest {


    private lateinit var repository: EventsRepository
    private val mockWebServer = MockWebServer()


    @Before
    fun setup() {

        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()




        val api = retrofit.create(EventService::class.java)



        repository = IEventsRepository(api)
    }

    @Test
    fun `should return list`() = runBlocking {

        val events = listOf(
            Event(
                people = emptyList(),
                date = 1534784400000,
                description = "O Patas Dadas estará na Redenção, nesse domingo, com cães para adoção e produtos à venda!\n\nNa ocasião, teremos bottons, bloquinhos e camisetas!\n\nTraga seu Pet, os amigos e o chima, e venha aproveitar esse dia de sol com a gente e com alguns de nossos peludinhos - que estarão prontinhos para ganhar o ♥ de um humano bem legal pra chamar de seu. \n\nAceitaremos todos os tipos de doação:\n- guias e coleiras em bom estado\n- ração (as que mais precisamos no momento são sênior e filhote)\n- roupinhas \n- cobertas \n- remédios dentro do prazo de validade",
                image = "http://lproweb.procempa.com.br/pmpa/prefpoa/seda_news/usu_img/Papel%20de%20Parede.png",
                longitude = -51.2146267,
                latitude = -30.0392981,
                price = 29.99,
                title = "Feira de adoção de animais na Redenção",
                id = "1"
            )
        ).toMutableList()

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(events)))


        val flow: Flow<DataResult<List<EventModel>>> = repository.getEvents()
        flow.test {
            assertEquals(DataResult.OnSuccess(events), awaitItem() )
            awaitComplete()
        }
    }

    @Test
    fun `should fail on server error`() = runBlocking {

        mockWebServer.enqueue(MockResponse().setResponseCode(500))


        val flow: Flow<DataResult<List<EventModel>>> = repository.getEvents()
        flow.test {
            assertThat(awaitItem(), CoreMatchers.instanceOf<Any>(DataResult.OnFailed::class.java))
            awaitComplete()
        }
    }

    @Test
    fun `should return empty list`() = runBlocking {

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(emptyList<Event>())))


        val flow: Flow<DataResult<List<EventModel>>> = repository.getEvents()
        flow.test {
            assertEquals(DataResult.OnSuccess(emptyList<Event>()), awaitItem() )
            awaitComplete()
        }
    }

    @ExperimentalTime
    @Test
    fun `should fail on slow connection`() = runBlocking {

        val events = listOf(
            Event(
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
        ).toMutableList()

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(events)).setBodyDelay(30, TimeUnit.SECONDS))


        val flow: Flow<DataResult<List<EventModel>>> = repository.getEvents()
        flow.test(timeout = Duration.parse("30s")) {
            assertTrue(awaitItem() is DataResult.OnFailed)
            awaitComplete()
        }
    }

}