package ru.ktoddler.model.storage.converter;

import android.content.ContentValues;
import android.database.Cursor;

import nl.qbusict.cupboard.convert.EntityConverter;
import nl.qbusict.cupboard.convert.FieldConverter;
import ru.ktoddler.util.ConvertUtils;
import ru.ktoddler.util.NpeUtils;

public class LongArrayFieldConverter implements FieldConverter<long[]> {

    @Override
    public long[] fromCursorValue(Cursor cursor, int columnIndex) {
        String data = cursor.getString(columnIndex);
        if (NpeUtils.isEmpty(data)) {
            return new long[0];
        }

        String[] stringArray = data.split(",");
        long[] longArray = new long[stringArray.length];
        int size = stringArray.length;
        for (int i = 0; i < size; i++) {
            longArray[i] = ConvertUtils.parseLong(stringArray[i]);
        }
        return longArray;
    }

    @Override
    public void toContentValue(long[] value, String key, ContentValues values) {
        values.put(key, ConvertUtils.join(NpeUtils.getNonNull(value), ","));
    }

    @Override
    public EntityConverter.ColumnType getColumnType() {
        return EntityConverter.ColumnType.INTEGER;
    }
}
