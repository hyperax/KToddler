package ru.ktoddler.model.storage

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import nl.qbusict.cupboard.CupboardBuilder
import nl.qbusict.cupboard.CupboardFactory
import nl.qbusict.cupboard.CupboardFactory.cupboard
import ru.ktoddler.di.scope.PerApplication
import ru.ktoddler.model.entity.contract.Contract
import ru.ktoddler.model.storage.converter.BigDecimalFieldConverter
import ru.ktoddler.model.storage.converter.LongArrayFieldConverter
import ru.ktoddler.model.storage.converter.StringArrayFieldConverter
import java.math.BigDecimal
import javax.inject.Inject

@PerApplication
class ToddlerSQLiteOpenHelper
@Inject
constructor(context: Context) : SQLiteOpenHelper(context, ToddlerSQLiteOpenHelper.DB_FILE, null, ToddlerSQLiteOpenHelper.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        cupboard().withDatabase(db).createTables()
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        cupboard().withDatabase(db).upgradeTables()
    }

    companion object {

        private val DB_FILE = "toddler.db"
        private val DB_VERSION = 1

        init {
            val cupboard = CupboardBuilder()
                    .useAnnotations()
                    .registerFieldConverter(LongArray::class.java, LongArrayFieldConverter())
                    .registerFieldConverter(Array<String>::class.java, StringArrayFieldConverter())
                    .registerFieldConverter(BigDecimal::class.java, BigDecimalFieldConverter())
                    .build()

            for (clazz in Contract.ENTITIES) {
                cupboard.register(clazz)
            }

            CupboardFactory.setCupboard(cupboard)
        }
    }

}