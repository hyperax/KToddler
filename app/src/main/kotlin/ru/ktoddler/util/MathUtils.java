package ru.ktoddler.util;

import android.support.annotation.NonNull;

import java.math.BigDecimal;

public abstract class MathUtils {

    private MathUtils() {
    }

    /**
     * result of expression {@code first} > {@code second}
     * <br>{@code null} parameters is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean greater(BigDecimal first, BigDecimal second) {
        return NpeUtils.getNonNull(first).compareTo(NpeUtils.getNonNull(second)) == 1;
    }

    /**
     * result of expression {@code first} >= {@code second}
     * <br>{@code null} parameters is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean greaterOrEqual(BigDecimal first, BigDecimal second) {
        return NpeUtils.getNonNull(first).compareTo(NpeUtils.getNonNull(second)) >= 0;
    }

    /**
     * result of expression {@code first} == {@code second}
     * <br>{@code null} parameters is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean equal(BigDecimal first, BigDecimal second) {
        return NpeUtils.getNonNull(first).compareTo(NpeUtils.getNonNull(second)) == 0;
    }

    /**
     * result of expression {@code value} > {@link BigDecimal#ZERO}
     * <br>{@code null} parameter is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean isPositive(BigDecimal value) {
        return greater(value, BigDecimal.ZERO);
    }

    /**
     * result of expression {@code value} < {@link BigDecimal#ZERO}
     * <br>{@code null} parameter is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean isNegative(BigDecimal value) {
        return greater(BigDecimal.ZERO, value);
    }

    /**
     * Method parses entire string value and tries to extract {@link BigDecimal} value.
     * <br/> Method based on {@link BigDecimal#toPlainString()}
     * @param plainString string presentation of number value.
     * <br/> string will be trimmed
     * <br/> {@code null} or empty parameter is equivalent to {@link BigDecimal#ZERO}
     * @return {@link BigDecimal} presentation of string value or {@link BigDecimal#ZERO} if the parsing error
     */
    @NonNull
    public static BigDecimal getValue(String plainString) {
        BigDecimal resultValue;
        if (NpeUtils.isEmpty(plainString)) {
            resultValue = BigDecimal.ZERO;
        } else {
            try {
                resultValue = new BigDecimal(plainString.trim());
            } catch (Exception e) {
                resultValue = BigDecimal.ZERO;
            }
        }
        return resultValue;
    }

    /**
     * @param value {@link BigDecimal} presentation of number value.
     * <br/> {@code null} is equivalent to {@link BigDecimal#ZERO}
     * @return count of valuable characters after decimal separator
     */
    public static int getFractionalCount(BigDecimal value) {
        String string = NpeUtils.getNonNull(value)
                .stripTrailingZeros().toPlainString();
        int index = string.indexOf(".");
        return index < 0 ? 0 : string.length() - index - 1;
    }

    /**
     * @param value {@link BigDecimal} presentation of number value.
     * <br/> negative values are taken by {@link BigDecimal#abs()}
     * <br/> {@code null} is equivalent to {@link BigDecimal#ZERO}
     * @return count of valuable characters before decimal separator
     */
    public static int getIntegerCount(BigDecimal value) {
        int absIntValue = NpeUtils.getNonNull(value).abs().intValue();
        return absIntValue > 0? String.valueOf(absIntValue).length() : 0;
    }

    /**
     * returns {@code true} if result of expression {@code value / multiplicity} has zero remainder or multiplicity equals zero or {@code null}
     * <br/> otherwise returns {@code false}
     * <br/> null parameters is equivalent to {@link BigDecimal#ZERO}
     */
    public static boolean isValueMultiple(BigDecimal value, BigDecimal multiplicity) {
        return equal(NpeUtils.getNonNull(value), BigDecimal.ZERO)
                || equal(NpeUtils.getNonNull(multiplicity), BigDecimal.ZERO)
                || equal(value.remainder(multiplicity), BigDecimal.ZERO);
    }

    /**
     * returns string presentation of {@code value} based on {@link BigDecimal#toPlainString()} method
     * <br/> null parameter is equivalent to {@link BigDecimal#ZERO}
     */
    @NonNull
    public static String toPlainString(BigDecimal value) {
        return NpeUtils.getNonNull(value).toPlainString();
    }

    /**
     * returns integer part of result of expression {@code dividend / divisor}
     * <br/> or returns {@link BigDecimal#ZERO} if {@code divisor} is {@link BigDecimal#ZERO}
     * <br/> null parameters is equivalent to {@link BigDecimal#ZERO}
     */
    @NonNull
    public static BigDecimal divideInteger(BigDecimal dividend, BigDecimal divisor) {
        if (equal(NpeUtils.getNonNull(dividend), BigDecimal.ZERO)
                || equal(NpeUtils.getNonNull(divisor), BigDecimal.ZERO)) {
            return BigDecimal.ZERO;
        }

        return dividend.divide(divisor, BigDecimal.ROUND_FLOOR).setScale(0, BigDecimal.ROUND_DOWN);
    }

    public static int compare(long lhs, long rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static int compare(int lhs, int rhs) {
        return lhs < rhs ? -1 : (lhs == rhs ? 0 : 1);
    }

    public static int calcCapacity(int size, int partSize, int minCapacity) {
        if (partSize > 0 && size > 0) {
            return Math.max(minCapacity, size / partSize);
        }
        return minCapacity;
    }
}
