package com.br.guilherme.core.base

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

typealias NetworkAPIInvoke<T> = suspend () -> Response<T>


suspend fun <T : Any> networkCall(
    messageInCaseOfError: String = "Server error",
    networkApiCall: NetworkAPIInvoke<T>
): Flow<DataResult<T>> {
    return flow {
        val response = networkApiCall()
        if (response.isSuccessful) {
            response.body()?.let { emit(DataResult.OnSuccess(it)) } ?: emit(
                DataResult.OnFailed(
                    IOException("API call successful but empty response body")
                )
            )
            return@flow
        }
        emit(DataResult.OnFailed(IOException(response.message() ?: messageInCaseOfError)))
        return@flow
    }.catch { e ->

        if (e is UnknownHostException) {
            emit(DataResult.OnFailed(UnknownHostException("Verifique sua conexão com a internet e tente novamente.")))
            return@catch
        }
        if (e is SocketTimeoutException) {
            emit(DataResult.OnFailed(Exception("Tempo de conexão excedido")))
            return@catch
        }
        emit(DataResult.OnFailed(IOException("Favor informar o erro ao suporte: $e")))
    }
}