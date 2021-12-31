package com.br.guilherme.data.eventDetail.repository

import app.cash.turbine.test
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.data.eventDetail.remote.datasource.EventDetailService
import com.br.guilherme.data.eventDetail.remote.model.EventDetail
import com.br.guilherme.domain.eventDetail.EventDetailRepository
import com.br.guilherme.domain.model.EventModel
import com.google.gson.Gson
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


@InternalCoroutinesApi
@RunWith(JUnit4::class)
class EventDetailRepositoryTest {

    private lateinit var repository: EventDetailRepository
    private val mockWebServer = MockWebServer()

    @Before
    fun setup() {



        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()




        val api = retrofit.create(EventDetailService::class.java)



        repository = IEventDetailRepository(api)
    }


    @Test
    fun `should return event`() = runBlocking {

        val event =
            EventDetail(
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

        mockWebServer.enqueue(MockResponse().setBody(Gson().toJson(event)))


        val flow: Flow<DataResult<EventModel>> = repository.getEventDetail("1")
        flow.test {
            Assert.assertEquals(DataResult.OnSuccess(event), awaitItem() )
            awaitComplete()
        }
    }

    @Test
    fun `should fail on not found`() = runBlocking {


        mockWebServer.enqueue(MockResponse().setResponseCode(404))


        val flow: Flow<DataResult<EventModel>> = repository.getEventDetail("1")
        flow.test {
            MatcherAssert.assertThat(
                awaitItem(),
                CoreMatchers.instanceOf(DataResult.OnFailed::class.java)
            )
            awaitComplete()
        }
    }



}