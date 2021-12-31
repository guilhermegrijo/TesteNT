package com.br.guilherme.checkin

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.br.guilherme.checkin.ui.CheckInViewModel
import com.br.guilherme.domain.checkin.MakeCheckInUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentCaptor
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify


@RunWith(JUnit4::class)
class ViewModelTest {

    @Rule
    @JvmField
    var instantExecutorRule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CheckInViewModel

    private var usecase: MakeCheckInUseCase = mock()

    private val observer: Observer<Boolean> = mock()


    @Test
    fun `Form should be valid when all fields are valid`() = runBlocking {
        val isValid = true

        viewModel = CheckInViewModel(usecase)

        viewModel.valid.observeForever(observer)

        viewModel.email.postValue("Guilherme@email.com")
        viewModel.name.postValue("Guilherme")

        val captor = ArgumentCaptor.forClass(Boolean::class.java)

        verify(observer, times(4)).onChanged(captor.capture())
        assertThat(captor.value).isEqualTo(isValid)

    }

}