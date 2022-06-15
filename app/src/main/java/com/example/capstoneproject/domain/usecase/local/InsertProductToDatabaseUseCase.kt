package com.example.capstoneproject.domain.usecase.local

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class InsertProductToDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(product: Product) = flow {
        emit(Resource.Loading)
        try {
            val insertProduct = localRepository.insertProductToDatabase(product)
            emit(Resource.Success(insertProduct))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}