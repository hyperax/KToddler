package ru.ktoddler.model.storage.converter

import android.content.ContentValues
import android.database.Cursor
import nl.qbusict.cupboard.convert.EntityConverter
import nl.qbusict.cupboard.convert.FieldConverter
import ru.ktoddler.util.MathUtils
import java.math.BigDecimal

class BigDecimalFieldConverter : FieldConverter<BigDecimal> {
    override fun fromCursorValue(cursor: Cursor, columnIndex: Int): BigDecimal {
        return MathUtils.getValue(cursor.getString(columnIndex))
    }

    override fun toContentValue(value: BigDecimal?, key: String, contentValues: ContentValues) {
        contentValues.put(key, MathUtils.toPlainString(value))
    }

    override fun getColumnType(): EntityConverter.ColumnType {
        return EntityConverter.ColumnType.TEXT
    }
}