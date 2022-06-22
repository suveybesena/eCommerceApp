package com.example.capstoneproject.domain.usecase.firebase

import com.example.capstoneproject.common.Resource
import com.example.capstoneproject.data.model.AuthModel
import com.example.capstoneproject.di.IoDispatcher
import com.example.capstoneproject.domain.repository.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CreateUserUseCase @Inject constructor(
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    suspend fun invoke(authModel: AuthModel) = flow {
        emit(Resource.Loading)
        try {
            val auth = firebaseAuthRepository.signUpWithEmail(authModel)
            emit(Resource.Success(auth))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}