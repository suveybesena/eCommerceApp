package com.example.capstoneproject.domain.usecase.local.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class InsertProductToBasketUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(basketItem: Basket) = flow {
        emit(Resource.Loading)
        try {
            val insertProductToBasket = localRepository.insertProductToBasket(basketItem)
            emit(Resource.Success(insertProductToBasket))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}