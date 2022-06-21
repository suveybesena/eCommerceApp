package com.example.capstoneproject.domain.usecase.remote.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class DeleteProductFromBagUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(id: Int) = flow {
        emit(Resource.Loading)
        try {
            val deleteProduct = remoteRepository.deleteFromBag(id)
            emit(Resource.Success(deleteProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}