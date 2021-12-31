package com.br.guilherme.events

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.br.guilherme.core.CoroutineContextProvider
import com.br.guilherme.core.base.DataResult
import com.br.guilherme.domain.events.usecase.LoadAllEventsUseCase
import com.br.guilherme.domain.model.EventModel
import com.br.guilherme.events.model.EventItem
import com.br.guilherme.events.ui.EventsViewModel
import com.br.guilherme.events.ui.ScreenState
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import kotlin.coroutines.CoroutineContext


@RunWith(MockitoJUnitRunner::class)
class ViewModelTest {


    @Rule
    @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EventsViewModel

    private val events = listOf(
        event(
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
    )

    private var usecase: LoadAllEventsUseCase = mock()


    private val observer: Observer<ScreenState<List<EventItem>>> = mock()

    @Captor
    private lateinit var captor: ArgumentCaptor<ScreenState<List<EventItem>>>

    data class event(
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

    @ExperimentalCoroutinesApi
    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

    }

    @ExperimentalCoroutinesApi
    @Test
    fun `should observe state Loading and Success`() = runBlocking {

        viewModel = EventsViewModel(usecase, TestContextProvider())

        viewModel.data.observeForever(observer)

        usecase.stub { onBlocking { invoke(any()) } doReturn flowOf(DataResult.OnSuccess(events)) }

        viewModel.loadEvents()

        verify(observer, times(2)).onChanged(captor.capture())

        val values = captor.allValues

        assertEquals(true, values[0] is ScreenState.Loading)
        assertEquals(true, values[1] is ScreenState.Success)
    }
}


@ExperimentalCoroutinesApi
class TestContextProvider : CoroutineContextProvider() {

    private val testCoroutineDispatcher = TestCoroutineDispatcher()
    override val main: CoroutineContext = testCoroutineDispatcher
    override val io: CoroutineContext = testCoroutineDispatcher
}