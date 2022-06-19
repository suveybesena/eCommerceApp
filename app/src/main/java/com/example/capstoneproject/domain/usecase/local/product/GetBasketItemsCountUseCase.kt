package com.example.capstoneproject.domain.usecase.local.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject


class GetBasketItemsCountUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userId: String) = channelFlow {
        send(Resource.Loading)
        try {
            val collectionsCount = localRepository.getBasketProducts(userId)
            CoroutineScope(currentCoroutineContext()).launch {
                collectionsCount.collect { list ->
                    send(Resource.Success(list.size))
                }
            }
        } catch (e: Exception) {
            send(Resource.Error(e.localizedMessage))
            println(e.localizedMessage)
        }
    }.flowOn(ioDispatcher)
}