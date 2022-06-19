package com.example.capstoneproject.domain.usecase.remote.product

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.product.Product
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class GetProductsByCategoriesUseCase @Inject constructor(
    private val remoteRepository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(categoryName: String) = flow {
        emit(Resource.Loading)
        val mutableList = mutableListOf<Product>()
        try {
            val user = "suveybesenakucuk"
            val products = remoteRepository.getProductsByCategories(user, categoryName)
            products.forEach { product ->
                val productsItem = Product(
                    product.title, product.description,
                    product.category, product.price, product.image
                )
                mutableList.add(productsItem)
            }
            emit(Resource.Success(mutableList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}