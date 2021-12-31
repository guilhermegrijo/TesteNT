package com.br.guilherme.data.checkin.repository

import app.cash.turbine.test
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.data.eventCheckin.remote.datasource.CheckInService
import com.br.guilherme.data.eventCheckin.repository.ICheckInRepository
import com.br.guilherme.data.eventDetail.remote.datasource.EventDetailService
import com.br.guilherme.data.eventDetail.remote.model.EventDetail
import com.br.guilherme.domain.checkin.usecase.CheckInRepository
import com.br.guilherme.domain.model.CheckInModel
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
class CheckinRepositoryTest {

    private lateinit var repository: CheckInRepository
    private val mockWebServer = MockWebServer()


    @Before
    fun setup() {



        val okHttpClient: OkHttpClient = OkHttpClient.Builder().build()

        val retrofit = Retrofit.Builder()
            .baseUrl(mockWebServer.url("").toString())
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()




        val api = retrofit.create(CheckInService::class.java)



        repository = ICheckInRepository(api)
    }

    @Test
    fun `should create checkin`() = runBlocking {

        val request = CheckInModel("1", "Teste", "teste@teste.com")

        mockWebServer.enqueue(MockResponse().setResponseCode(201))


        val flow: Flow<DataResult<Unit>> = repository.makeCheckin(request)
        flow.test {
            assert(awaitItem() is DataResult.OnSuccess )
            awaitComplete()
        }
    }

    @Test
    fun `should fail on create checkin`() = runBlocking {

        val request = CheckInModel("1", "Teste", "teste@teste.com")

        mockWebServer.enqueue(MockResponse().setResponseCode(500))


        val flow: Flow<DataResult<Unit>> = repository.makeCheckin(request)
        flow.test {
            MatcherAssert.assertThat(
                awaitItem(),
                CoreMatchers.instanceOf<Any>(DataResult.OnFailed::class.java)
            )
            awaitComplete()
        }
    }



}