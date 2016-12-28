package ru.ktoddler.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ConvertUtils {
    private ConvertUtils() {
    }

    /**
     * Converts {@code array}  to non-null {@link List}.
     * @param array that will be converted to {@link List}
     * @return {@link List} of array type or {@link Collections#emptyList()} if {@code array} is {@code null}
     */
    @NonNull
    public static <T> List<T> toList(@Nullable T[] array) {
        List<T> list = new ArrayList<>();

        if (!NpeUtils.isEmpty(array)) {
            Collections.addAll(list, array);
        }
        return list;
    }

    /**
     * Converts {@code array} of {@code primitive long} to non-null {@link List} of {@link Long}.
     * @param array that will be converted to {@link List}
     * @return {@link List} of array {@link Long} or {@link Collections#emptyList()} if {@code array} is {@code null}
     */
    @NonNull
    public static List<Long> toList(long... array) {
        int size = NpeUtils.getNonNull(array).length;
        List<Long> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * Converts {@code array} of {@code primitive long} to non-null {@link List} of {@link Integer}.
     * @param array that will be converted to {@link List}
     * @return {@link List} of array {@link Integer} or {@link Collections#emptyList()} if {@code array} is {@code null}
     */
    @NonNull
    public static List<Integer> toList(int... array) {
        int size = NpeUtils.getNonNull(array).length;
        List<Integer> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * @param str the character sequence from which to retrieve the md5 hash
     *            a {@code null} parameter is equivalent to the sequence with the zero length
     * @return MD5 hash in the string representation
     */
    @NonNull
    public static String toMd5(CharSequence str) {
        String toConvertStr = str == null? NpeUtils.EMPTY_STRING : str.toString();

        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(toConvertStr.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    /** Join {@code iterable} with {@code delimiter} as a separator and returns it as {@link String}
     * {@code null} items of array or {@code null} delimiter will be recognized as "null" string value.
     * @param iterable an array objects to be joined. Strings will be formed from
     *     the objects by calling object.toString().
     * @param delimiter an char sequence that will be separate items of {@code array}.
     * @return a string containing the tokens joined by {@code delimiters}.
     * <br/> Returns zero-length string on {@code null} {@code array} parameter
     */
    public static String join(Iterable iterable, CharSequence delimiter) {
        if (iterable == null) {
            return NpeUtils.EMPTY_STRING;
        }

        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token: iterable) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /** Join {@code iterable} with {@code delimiter} as a separator and returns it as {@link String}
     * {@code null} items of array or {@code null} delimiter will be recognized as "null" string value.
     * @param array an array objects to be joined. Strings will be formed from
     *     the objects by calling object.toString().
     * @param delimiter an char sequence that will be separate items of {@code array}.
     * @return a string containing the tokens joined by {@code delimiters}.
     * <br/> Returns zero-length string on {@code null} {@code array} parameter
     */
    public static String join(Object[] array, CharSequence delimiter) {
        if (array == null) {
            return NpeUtils.EMPTY_STRING;
        }

        StringBuilder sb = new StringBuilder();
        boolean firstTime = true;
        for (Object token: array) {
            if (firstTime) {
                firstTime = false;
            } else {
                sb.append(delimiter);
            }
            sb.append(token);
        }
        return sb.toString();
    }

    /** Join {@code iterable} with {@code delimiter} as a separator and returns it as {@link String}
     * {@code null} items of array or {@code null} delimiter will be recognized as "null" string value.
     * @param array an array objects to be joined. Strings will be formed from
     *     the objects by calling object.toString().
     * @param delimiter an char sequence that will be separate items of {@code array}.
     * @return a string containing the tokens joined by {@code delimiters}.
     * <br/> Returns zero-length string on {@code null} {@code array} parameter
     */
    public static String join(long[] array, CharSequence delimiter) {
        if (array == null) {
            return NpeUtils.EMPTY_STRING;
        }

        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i];
            if (i != array.length - 1) {
                result += delimiter;
            }
        }
        return result;
    }

    /** Join {@code iterable} with {@code delimiter} as a separator and returns it as {@link String}
     * {@code null} items of array or {@code null} delimiter will be recognized as "null" string value.
     * @param array an array objects to be joined. Strings will be formed from
     *     the objects by calling object.toString().
     * @param delimiter an char sequence that will be separate items of {@code array}.
     * @return a string containing the tokens joined by {@code delimiters}.
     * <br/> Returns zero-length string on {@code null} {@code array} parameter
     */
    public static String join(int[] array, CharSequence delimiter) {
        if (array == null) {
            return "";
        }

        String result = "";
        for (int i = 0; i < array.length; i++) {
            result += array[i];
            if (i != array.length - 1) {
                result += delimiter;
            }
        }
        return result;
    }

    /**
     * @param s sequence to parse. May be null.
     * @return {@code long}. Return {@code 0L} on {@code null} or zero-length sequence.
     */
    public static long parseLong(CharSequence s) {
        long result;

        try {
            result = Long.parseLong(s.toString());
        } catch (Exception e) {
            result = 0L;
        }

        return result;
    }

    /**
     * @param s sequence to parse. May be null.
     * @return {@code long}. Return {@code 0L} on {@code null} or zero-length sequence.
     */
    public static int parseInt(CharSequence s) {
        int result;

        try {
            result = Integer.parseInt(s.toString());
        } catch (Exception e) {
            result = 0;
        }

        return result;
    }

    /**
     * Converts {@link List} of {@link Long} to non-null long array.
     * <br/>{@code Null} entries will be recognized as {@code 0L}.
     */
    @NonNull
    public static long[] toLongArray(List<Long> list) {
        int size = NpeUtils.getNonNull(list).size();
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = NpeUtils.getNonNull(list.get(i));
        }
        return array;
    }

    /**
     * Converts {@link List} of {@link Integer} to non-null int array.
     * {@code Null} entries will be recognized as {@code 0}.
     */
    @NonNull
    public static int[] toIntArray(List<Integer> list) {
        int size = NpeUtils.getNonNull(list).size();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = NpeUtils.getNonNull(list.get(i));
        }
        return array;
    }

    /**
     * Converts {@link List} type to nullable array.
     * @param list nullable list of data.
     * @return array of parameter list type. Method returns {@code null} if {@code list} is {@code null} or has zero-length.
     */
    @SuppressWarnings("unchecked")
    @Nullable
    public static <T> T[] toArray(List<T> list) {
        if (NpeUtils.isEmpty(list)) {
            return null;
        } else {
            T[] array = (T[]) Array.newInstance(NpeUtils.getComponentClass(list), list.size());
            return list.toArray(array);
        }
    }

    /**
     * Converts {@link List} type to non-null array.
     * @param list nullable list of data
     * @param clazz type of result array, it is required to support non-null result.
     *              <br/> Important - Primitive types doesn't supported
     */
    @SuppressWarnings("unchecked")
    @NonNull
    public static <T> T[] toArray(List<T> list, @NonNull Class<T> clazz) {
        if (NpeUtils.isEmpty(list)) {
            return (T[]) Array.newInstance(clazz, 0);
        } else {
            T[] array = (T[]) Array.newInstance(NpeUtils.getComponentClass(list), list.size());
            return list.toArray(array);
        }
    }

    @NonNull
    public static <T> List<T> concat(@Nullable List<T> first, @Nullable List<T> second) {
        ArrayList<T> result = new ArrayList<>(NpeUtils.getNonNull(first));
        result.addAll(NpeUtils.getNonNull(second));
        return result;
    }

    @NonNull
    public static <T> List<T> concat(@Nullable List<T> first, T[] second) {
        ArrayList<T> result = new ArrayList<>(NpeUtils.getNonNull(first));
        result.addAll(toList(second));
        return result;
    }
}
