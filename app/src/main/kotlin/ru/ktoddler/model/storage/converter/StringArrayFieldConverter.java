package ru.ktoddler.model.storage.converter;

import android.content.ContentValues;
import android.database.Cursor;

import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.FieldConverter;
import ru.ktoddler.util.ConvertUtils;
import ru.ktoddler.util.NpeUtils;

public class StringArrayFieldConverter implements FieldConverter<String[]> {

    @Override
    public String[] fromCursorValue(Cursor cursor, int columnIndex) {
        String data = cursor.getString(columnIndex);
        if (NpeUtils.isEmpty(data)) {
            return new String[0];
        }
        return data.split(",");
    }

    @Override
    public void toContentValue(String[] value, String key, ContentValues values) {
        values.put(key, ConvertUtils.join(NpeUtils.getNonNull(value), ","));
    }

    @Override
    public EntityConverter.ColumnType getColumnType() {
        return EntityConverter.ColumnType.TEXT;
    }
}
