package ru.ktoddler.model.storage.converter

import android.content.ContentValues
import android.database.Cursor

import nl.qbusict.cupboard.convert.EntityConverter
import nl.qbusict.cupboard.convert.FieldConverter
import ru.ktoddler.util.ConvertUtils
import ru.ktoddler.util.NpeUtils

class StringArrayFieldConverter : FieldConverter<Array<String>> {

    override fun fromCursorValue(cursor: Cursor, columnIndex: Int): Array<String> {
        val data = cursor.getString(columnIndex)
        if (NpeUtils.isEmpty(data)) {
            return arrayOf()
        }
        return data.split(",".toRegex()).dropLastWhile(String::isEmpty).toTypedArray()
    }

    override fun toContentValue(value: Array<String>?, key: String, values: ContentValues) {
        values.put(key, ConvertUtils.join(NpeUtils.getNonNull(value), ","))
    }

    override fun getColumnType(): EntityConverter.ColumnType {
        return EntityConverter.ColumnType.TEXT
    }
}
