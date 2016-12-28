package ru.ktoddler.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.annimon.stream.function.Consumer;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NpeUtils {

    public static final String[] EMPTY_STRING_ARRAY = new String[0];
    public static final long[] EMPTY_LONG_ARRAY = new long[0];
    public static final int[] EMPTY_INT_ARRAY = new int[0];
    public static final String EMPTY_STRING = "";
    private static final long EMPTY_LONG = 0L;
    private static final Integer EMPTY_INTEGER = 0;

    @NonNull
    public static <T> T getNonNull(T value, @NonNull T ifNullValue) {
        return value == null? ifNullValue : value;
    }

    @NonNull
    public static <T> List<T> getNonNull(List<T> value) {
        return value == null? (List<T>) Collections.emptyList() : value;
    }

    public static long getNonNull(Long value) {
        return getNonNull(value, EMPTY_LONG);
    }

    public static int getNonNull(Integer value) {
        return getNonNull(value, EMPTY_INTEGER);
    }

    @NonNull
    public static String getNonNull(String value) {
        return getNonNull(value, EMPTY_STRING);
    }

    @NonNull
    public static long[] getNonNull(long[] value) {
        return getNonNull(value, EMPTY_LONG_ARRAY);
    }

    @NonNull
    public static String[] getNonNull(String[] value) {
        return getNonNull(value, EMPTY_STRING_ARRAY);
    }

    @NonNull
    public static int[] getNonNull(int[] value) {
        return getNonNull(value, EMPTY_INT_ARRAY);
    }

    @NonNull
    public static BigDecimal getNonNull(BigDecimal value) {
        return getNonNull(value, BigDecimal.ZERO);
    }

    public static CharSequence getNonNull(CharSequence charSequence) {
        return charSequence == null? EMPTY_STRING : charSequence;
    }

    public static <T> boolean isEmpty(T[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(int[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(long[] array) {
        return array == null || array.length == 0;
    }

    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static boolean isEmpty(String string) {
        return TextUtils.isEmpty(string);
    }

    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean equals(Object a, Object b) {
        return (a == null) ? (b == null) : a.equals(b);
    }

    @Nullable
    public static Class getComponentClass(Collection<?> list) {
        return isEmpty(list)? null : list.iterator().next().getClass();
    }

    public static <T> boolean call(@NonNull Object instance,
                                   @NonNull Class<T> clazz,
                                   @NonNull Consumer<T> consumer) {
        if (clazz.isInstance(instance)) {
            consumer.accept(clazz.cast(instance));
            return true;
        }
        return false;
    }

    public static <T> boolean call(@Nullable T instance,
                                   @NonNull Consumer<T> consumer) {
        if (instance != null) {
            consumer.accept(instance);
            return true;
        }
        return false;
    }

    @NonNull
    public static String getNonEmpty(@Nullable String value, @NonNull String ifEmpty) {
        return NpeUtils.isEmpty(value)? ifEmpty : value;
    }
}
