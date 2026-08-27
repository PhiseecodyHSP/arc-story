package io.github.phiseecodyhsp.arcstory.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MathUtilTest {

    @Test
    @DisplayName("intToOrdinal 传入个位数不为 1, 2, 3 时应添加 -th 后缀")
    void intToOrdinal_whenUnitPlaceNot123_returnsSpecialSuffixes() {
        assertEquals("4th", MathUtil.intToOrdinal(4));
        assertEquals("16th", MathUtil.intToOrdinal(16));
        assertEquals("85th", MathUtil.intToOrdinal(85));
        assertEquals("2026th", MathUtil.intToOrdinal(2026));
    }

    @Test
    @DisplayName("intToOrdinal 传入十位数不为 1 时应可以添加 -st, -rd, -nd 后缀")
    void intToOrdinal_whenTenPlaceNot1_returnsSpecialSuffixes() {
        assertEquals("1st", MathUtil.intToOrdinal(1));
        assertEquals("2nd", MathUtil.intToOrdinal(2));
        assertEquals("3rd", MathUtil.intToOrdinal(3));
        assertEquals("21st", MathUtil.intToOrdinal(21));
        assertEquals("622nd", MathUtil.intToOrdinal(622));
    }

    @Test
    @DisplayName("intToOrdinal 传入十位数为 1 时应统一添加 -th 后缀")
    void intToOrdinal_whenTenPlace1_returnsSpecialSuffixes() {
        assertEquals("11th", MathUtil.intToOrdinal(11));
        assertEquals("12th", MathUtil.intToOrdinal(12));
        assertEquals("13th", MathUtil.intToOrdinal(13));
        assertEquals("211th", MathUtil.intToOrdinal(211));
    }

    @Test
    @DisplayName("intToOrdinal 传入负数时, 应返回加负号前缀的传入正数的结果")
    void intToOrdinal_whenNegative_returnsNegativePrefixes() {
        assertEquals("-" + MathUtil.intToOrdinal(1), MathUtil.intToOrdinal(-1));
        assertEquals("-" + MathUtil.intToOrdinal(12), MathUtil.intToOrdinal(-12));
        assertEquals("-" + MathUtil.intToOrdinal(235), MathUtil.intToOrdinal(-235));
    }

    @Test
    @DisplayName("doubleToString 传入正整数时, 应返回不带小数点的正数")
    void doubleToString_whenPositiveInteger_returnsPositiveNumberWithoutDecimalPoint() {
        assertEquals("1", MathUtil.doubleToString(1.0D));
        assertEquals("14", MathUtil.doubleToString(14.0D));
        assertEquals("514", MathUtil.doubleToString(514.0D));
    }

    @Test
    @DisplayName("doubleToString 传入负整数时, 应返回加负号前缀的传入正整数的结果")
    void doubleToString_whenNegativeInteger_returnsNegativeNumberWithoutDecimalPoint() {
        assertEquals("-1", MathUtil.doubleToString(-1.0D));
        assertEquals("-14", MathUtil.doubleToString(-14.0D));
        assertEquals("-514", MathUtil.doubleToString(-514.0D));
    }

    @Test
    @DisplayName("doubleToString 传入正小数时, 应返回带小数点的正数 (末尾不含 0 )")
    void doubleToString_whenPositiveDecimal_returnsPositiveNumberWithDecimalPoint() {
        assertEquals("1.1", MathUtil.doubleToString(1.1D));
        assertEquals("45.14", MathUtil.doubleToString(45.14D));
        assertEquals("191.981", MathUtil.doubleToString(191.9810D));
    }

    @Test
    @DisplayName("doubleToString 传入负小数时, 应返回加负号前缀的传入正小数的结果")
    void doubleToString_whenNegativeDecimal_returnsNegativeNumberWithDecimalPoint() {
        assertEquals("-1.1", MathUtil.doubleToString(-1.1D));
        assertEquals("-45.14", MathUtil.doubleToString(-45.14D));
        assertEquals("-191.981", MathUtil.doubleToString(-191.9810D));
    }

    @Test
    @DisplayName("ratingToLevel 传入的数小数部分小于 0.7 时, 应返回这个数的整数部分")
    void ratingToLevel_whenDecimalPartLessThanZeroPointSeven_returnsIntegerPart() {
        assertEquals("-3", MathUtil.ratingToLevel(-2.7D));
        assertEquals("0", MathUtil.ratingToLevel(0.4D));
        assertEquals("5", MathUtil.ratingToLevel(5.2D));
    }

    @Test
    @DisplayName("ratingToLevel 传入的数小数部分大于等于 0.7 时, 应返回这个数的整数部分并在末尾加上加号")
    void ratingToLevel_whenDecimalPartGreaterThanZeroPointSeven_returnsIntegerPartWithPlus() {
        assertEquals("-11+", MathUtil.ratingToLevel(-10.2D));
        assertEquals("0+", MathUtil.ratingToLevel(0.8D));
        assertEquals("25+", MathUtil.ratingToLevel(25.9D));
    }

    @Test
    @DisplayName("ratingToLevel 传入的数为 0 时, 应返回单个问号")
    void ratingToLevel_whenZero_returnsQuestionMark() {
        assertEquals("?", MathUtil.ratingToLevel(0.0D));
        assertEquals("?", MathUtil.ratingToLevel(-0.0D));
    }
}
