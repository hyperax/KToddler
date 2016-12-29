package ru.ktoddler.model.repository

import com.annimon.stream.Optional
import io.reactivex.Observable

interface LocalRepository {

    operator fun <T : Any> get(entity: Class<T>): List<T>

    operator fun <T : Any> get(entity: Class<T>, id: Long): Optional<T>

    fun <T : Any> put(entity: T)

    fun <T : Any> put(entity: List<T>)

    fun beginTransaction()

    fun setTransactionSuccessful()

    fun endTransaction()

    fun <T : Any> delete(entity: Class<T>): Int

    fun <T : Any> delete(entity: Class<T>, vararg ids: Long): Int

    fun <T : Any> delete(entity: T): Boolean

    fun changes(entityClass: Class<*>): Observable<Long>
}
