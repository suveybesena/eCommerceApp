package com.example.capstoneproject.domain.usecase.local.user

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.entities.user.User
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class InsertUserToDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(user: User) = flow {
        emit(Resource.Loading)
        try {
            val insertUser = localRepository.insertUserToDatabase(user)
            emit(Resource.Success(insertUser))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}