package com.example.capstoneproject.domain.usecase.remote

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.model.InsertedProduct
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreateProductUseCase @Inject constructor(
    private val repository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(product: InsertedProduct) = flow {
        emit(Resource.Loading)
        try {
            val createProduct = repository.createProduct(product)
            emit(Resource.Success(createProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}