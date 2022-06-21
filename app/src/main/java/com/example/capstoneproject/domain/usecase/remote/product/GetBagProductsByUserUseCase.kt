package com.example.capstoneproject.domain.usecase.remote.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Basket
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetBagProductsByUserUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userId: String) = flow {
        emit(Resource.Loading)
        val mutableList = mutableListOf<Basket>()
        try {
            remoteRepository.getBagProductsByUser(userId).forEach { product ->
                val productsItem = Basket(
                    product.title, product.description, product.count, product.user,
                    product.price, product.image
                )
                mutableList.add(productsItem)
            }
            emit(Resource.Success(mutableList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}