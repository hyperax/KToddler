package ru.ktoddler.model.storage.converter

import android.content.ContentValues
import android.database.Cursor

import nl.qbusict.cupboard.convert.EntityConverter
import nl.qbusict.cupboard.convert.FieldConverter
import ru.ktoddler.util.ConvertUtils
import ru.ktoddler.util.NpeUtils

class LongArrayFieldConverter : FieldConverter<LongArray> {

    override fun fromCursorValue(cursor: Cursor, columnIndex: Int): LongArray {
        val data = cursor.getString(columnIndex)
        if (NpeUtils.isEmpty(data)) {
            return LongArray(0)
        }

        val stringArray = data.split(",".toRegex())
                .dropLastWhile(String::isEmpty)
                .toTypedArray()
        val longArray = LongArray(stringArray.size)
        val size = stringArray.size
        for (i in 0..size - 1) {
            longArray[i] = ConvertUtils.parseLong(stringArray[i])
        }
        return longArray
    }

    override fun toContentValue(value: LongArray?, key: String, values: ContentValues) {
        values.put(key, ConvertUtils.join(NpeUtils.getNonNull(value), ","))
    }

    override fun getColumnType(): EntityConverter.ColumnType {
        return EntityConverter.ColumnType.INTEGER
    }
}
