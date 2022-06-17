package com.example.capstoneproject.domain.usecase.local.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Purchased
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class InsertProductToPurchasedUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(purchased: Purchased) = flow {
        emit(Resource.Loading)
        try {
            val insertPurchased = localRepository.insertProductToPurchased(purchased)
            emit(Resource.Success(insertPurchased))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}