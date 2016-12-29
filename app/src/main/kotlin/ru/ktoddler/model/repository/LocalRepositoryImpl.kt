package ru.ktoddler.model.repository

import android.database.sqlite.SQLiteDatabase
import com.annimon.stream.Optional
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.subjects.PublishSubject
import nl.qbusict.cupboard.CupboardFactory
import nl.qbusict.cupboard.DatabaseCompartment
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.entity.contract.Contract
import ru.ktoddler.util.DateUtils
import ru.ktoddler.util.NpeUtils
import ru.ktoddler.util.SqlUtils.`in`
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@PerApplication
class LocalRepositoryImpl
@Inject
constructor(private val sql: SQLiteDatabase) : LocalRepository {
    private val db: DatabaseCompartment = CupboardFactory.cupboard().withDatabase(sql)

    private val changesBus = PublishSubject.create<Class<*>>()

    private fun onDataChanged(entityClass: Class<*>) {
        changesBus.onNext(entityClass)
    }

    override fun <T : Any> put(entities: List<T>) {
        if (!NpeUtils.isEmpty(entities)) {
            db.put(entities)
            onDataChanged(NpeUtils.getComponentClass(entities)!!)
        }
    }

    override fun <T : Any> get(entity: Class<T>): List<T> {
        val query = db.query(entity)
        return query.list()
    }

    override fun <T : Any> get(entityClass: Class<T>, id: Long): Optional<T> {
        return Optional.ofNullable(db.get(entityClass, id))
    }

    override fun <T : Any> put(entity: T) {
        db.put(entity)
        onDataChanged(entity.javaClass)
    }

    override fun beginTransaction() {
        sql.beginTransaction()
    }

    override fun setTransactionSuccessful() {
        sql.setTransactionSuccessful()
    }

    override fun endTransaction() {
        sql.endTransaction()
    }

    override fun <T : Any> delete(entityClass: Class<T>): Int {
        val deletedCount = db.delete(entityClass, null)
        if (deletedCount > 0) {
            onDataChanged(entityClass)
        }
        return deletedCount
    }

    override fun <T : Any> delete(entity: Class<T>, vararg ids: Long): Int {
        val deletedCount = db.delete(entity, `in`(Contract.ID, ids))
        if (deletedCount > 0) {
            onDataChanged(entity)
        }
        return deletedCount
    }

    override fun <T : Any> delete(entity: T): Boolean {
        val isDeleted = db.delete(entity)
        if (isDeleted) {
            onDataChanged(entity.javaClass)
        }
        return isDeleted
    }

    override fun changes(entityClass: Class<*>): Observable<Long> {
        return changesBus.hide()
                .filter { changedEntityClass -> entityClass == changedEntityClass }
                .map<Long>(Function({ entity -> DateUtils.getCurrentMillis() }))
                .debounce(ENTITY_CHANGES_TIMEOUT.toLong(), TimeUnit.MILLISECONDS)
    }

    companion object {

        private val ENTITY_CHANGES_TIMEOUT = 300
    }

}
