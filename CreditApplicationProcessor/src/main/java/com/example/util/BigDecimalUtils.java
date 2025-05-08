package com.example.util;

import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Утилитные значения и методы для работы с {@link BigDecimal} значениями.
 */
@UtilityClass
public class BigDecimalUtils {

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal FIFTY = new BigDecimal(50);

    /**
     * Рассчитать проценты от числа.
     *
     * @param base число, от которого рассчитать проценты
     * @param pct значение процетов
     * @return результат
     */
    public static BigDecimal percentsOf(BigDecimal base, BigDecimal pct){
        return base.multiply(pct).divide(ONE_HUNDRED, 2, RoundingMode.HALF_UP);
    }

    /**
     * Рассчитать разницу в процентах между числами.
     *
     * @param one одно число
     * @param another другое число
     * @return результат в процентах
     */
    public static BigDecimal percentsDiff(BigDecimal one, BigDecimal another){
        return one.divide(another, 2, RoundingMode.HALF_UP).multiply(ONE_HUNDRED);
    }
}
