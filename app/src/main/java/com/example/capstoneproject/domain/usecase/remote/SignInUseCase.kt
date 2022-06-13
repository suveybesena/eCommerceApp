package com.example.capstoneproject.domain.usecase.remote

import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SignInUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userName: String, userPassword: String) = flow {
        emit(Resource.Loading)
        try {
            val token = remoteRepository.signIn(userName, userPassword)
            emit(Resource.Success(token))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            //println(e.localizedMessage)
        }
    }.flowOn(ioDispatcher)
}