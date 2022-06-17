package com.example.capstoneproject.domain.usecase.remote.user

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SignUpWithAPIUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(
        email: String,
        password: String,
        name: String,
        phone: String,
        address: String
    ) = flow {
        emit(Resource.Loading)
        try {
            val signUp = remoteRepository.signUp(email, password, name, phone, address)
            emit(Resource.Success(signUp))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}