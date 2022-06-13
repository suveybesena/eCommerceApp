package com.example.capstoneproject.domain.usecase.remote


import com.example.capstoneproject.common.extensions.Resource
import com.example.capstoneproject.data.model.user.UserItem
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.RemoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SingUpUseCase @Inject constructor(
    private val repository: RemoteRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userItem: UserItem) = flow {
        emit(Resource.Loading)
        try {
            val signUp = repository.signUp(userItem)
            emit(Resource.Success(signUp))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}