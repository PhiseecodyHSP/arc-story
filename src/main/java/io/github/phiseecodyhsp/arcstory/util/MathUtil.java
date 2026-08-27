package io.github.phiseecodyhsp.arcstory.util;

import io.github.phiseecodyhsp.arcstory.model.Chart;

public class MathUtil {

    /**
     * √2. 可以用来求对角线.
     */
    public static final double SQRT_2 = Math.sqrt(2.0D);

    /**
     * √3.
     */
    public static final double SQRT_3 = Math.sqrt(3.0D);

    /**
     * 将基数词改为序数词.
     *
     * @param num 数字
     * @return 加了序数后缀的数字
     */
    public static String intToOrdinal(int num) {
        int abs = Math.abs(num);

        // 请记住十位数为 1 的时候加 -th 而不是其它后缀.
        if ((abs / 10) % 10 != 1) {
            switch (abs % 10) {
                case 1 -> {
                    return num + "st";
                }
                case 2 -> {
                    return num + "nd";
                }
                case 3 -> {
                    return num + "rd";
                }
            }
        }

        return num + "th";
    }

    /**
     * 将一个双精度浮点数转换为字符串, 且当这个数是整数时, 返回的字符串中不含 ".0".
     *
     * <p>用于 {@link Chart} 的构造函数.
     *
     * @param num 要转换的双精度浮点数
     * @return 转换结果
     */
    public static String doubleToString(double num) {
        return num % 1.0D == 0.0D ? String.valueOf((int) num) : String.valueOf(num);
    }

    /**
     * 将定数转换为难度标级.
     *
     * <p>用于 {@link Chart} 的构造函数.
     *
     * @param rating 定数
     * @return 难度标级
     */
    public static String ratingToLevel(double rating) {
        if(rating == 0.0D) {
            return "?";
        }
        int intRating = (int) rating;
        if (rating < 0) {
            intRating--;
        }
        return rating - intRating < 0.7D ? String.valueOf(intRating) : intRating + "+";
    }

    /**
     * 给一个双精度浮点数平方.
     *
     * <p><b>使用说明:</b> 如果能不额外引入局部变量, 那就<b>不要</b>调用这个方法, 保持简洁.
     *
     * @param value 要平方的数
     * @return 平方结果
     */
    public static double square(double value) {
        return value * value;
    }

    /**
     * 给一个双精度浮点数立方.
     *
     * <p><b>使用说明:</b> 如果能不额外引入局部变量, 那就<b>不要</b>调用这个方法, 保持简洁.
     *
     * @param value 要立方的数
     * @return 立方结果
     */
    public static double cube(double value) {
        return value * value * value;
    }
}
