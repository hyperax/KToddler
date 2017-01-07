package ru.ktoddler.model.storage.converter

import android.content.ContentValues
import android.database.Cursor
import nl.qbusict.cupboard.convert.EntityConverter
import nl.qbusict.cupboard.convert.FieldConverter
import ru.ktoddler.util.toBigDecimal
import ru.ktoddler.util.toStr
import java.math.BigDecimal

class BigDecimalFieldConverter : FieldConverter<BigDecimal> {
    override fun fromCursorValue(cursor: Cursor, columnIndex: Int): BigDecimal {
        return cursor.getString(columnIndex).toBigDecimal()
    }

    override fun toContentValue(value: BigDecimal?, key: String, contentValues: ContentValues) {
        contentValues.put(key, value.toStr())
    }

    override fun getColumnType(): EntityConverter.ColumnType {
        return EntityConverter.ColumnType.TEXT
    }
}