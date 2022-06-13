package com.example.capstoneproject.domain.usecase.remote

import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetUsersUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke() = flow {
        val list = remoteRepository.getUsers()
        emit(Resource.Loading)
        try {
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}