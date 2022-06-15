package com.example.capstoneproject.domain.usecase.local

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.LocalRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class SignInWithDatabaseUseCase @Inject constructor(
    private val localRepository: LocalRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(userName: String, userPassword: String) = flow {
        emit(Resource.Loading)
        try {
            val login = localRepository.login(userName, userPassword)
            emit(Resource.Success(login))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}