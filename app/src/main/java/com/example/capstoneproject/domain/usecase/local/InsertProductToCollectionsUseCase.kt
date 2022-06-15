package com.example.capstoneproject.domain.usecase.local

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Collection
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class InsertProductToCollectionsUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(collection: Collection) = flow {
        emit(Resource.Loading)
        try {
            val insertProduct = localRepository.insertProductToCollections(collection)
            emit(Resource.Success())
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}