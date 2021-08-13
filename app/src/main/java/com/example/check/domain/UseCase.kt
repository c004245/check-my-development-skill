package com.example.check.domain

import com.example.check.util.Result
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class UseCase<in P, R>(val coroutineDispatcher: CoroutineDispatcher) {

    /** Executes the use case asynchronously and return a [Result].
     *
     * @return a [Result]
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): com.example.check.util.Result<R> {
        //Moving all use case's execution to the injected dispatcher
        //In production code, this is usually the Default dispatcher (background thread)
        //In tests, this becomes a TestCoroutineDispatcher
        return withContext(coroutineDispatcher) {
            execute(parameters)
        }
    }

    /**
     * Override this to set the code to be executed
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun CoroutineScope.execute(parameters: P): com.example.check.util.Result<R>
}


/**
 * Executes business logic synchronously or asynchronously using Coroutines.
 */
abstract class CompletableUseCase<in P, R>(val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO) {

    /** Executes the use case asynchronously and returns a [Result].
     *
     * @return a [Result].
     *
     * @param parameters the input parameters to run the use case with
     */
    suspend operator fun invoke(parameters: P): com.example.check.util.Result.Success<R> {
        // Moving all use case's executions to the injected dispatcher
        // In production code, this is usually the Default dispatcher (background thread)
        // In tests, this becomes a TestCoroutineDispatcher
        return withContext(coroutineDispatcher) {
            com.example.check.util.Result.Success(execute(parameters))
        }
    }

    /**
     * Override this to set the code to be executed.
     */
    @Throws(RuntimeException::class)
    protected abstract suspend fun CoroutineScope.execute(parameters: P): R
}

/**
 * Executes business logic in its execute method and keep posting updates to the results as
 * [Result<R>].
 * Handling an exception (emit [Result.OldError] to the result) is the subclassses's responsibility.
 */
abstract class FlowUseCase<in P, R>(private val coroutineDispatcher: CoroutineDispatcher) {

    @ExperimentalCoroutinesApi
    suspend operator fun invoke(parameters: P): Flow<Result<R>> {
        return execute(parameters).flowOn(coroutineDispatcher)
    }

    /**
     * If invalid argument or return value is catched. it will be handled in invoke function.
     */
    abstract suspend fun execute(paramters: P): Flow<com.example.check.util.Result<R>>
}