package com.infrastructure.utils;

/**
 * Created by Jaunty on 2016/12/10.
 */

public class Utils {

    /**
     * 安全类型转换工具，转int
     * @param value
     * @param defaultValue
     * @return
     */
    public final static int convertToInt(Object value, int defaultValue) {
        if(value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Integer.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).intValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换工具，转long
     * @param value
     * @param defaultValue
     * @return
     */
    public final static long convertToLong(Object value, long defaultValue) {
        if(value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Long.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).longValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换工具，转float
     * @param value
     * @param defaultValue
     * @return
     */
    public final static float convertToFloat(Object value, float defaultValue) {
        if(value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Float.valueOf(value.toString());
        } catch (Exception e) {
            try {
                return Double.valueOf(value.toString()).floatValue();
            } catch (Exception e1) {
                return defaultValue;
            }
        }
    }

    /**
     * 安全类型转换工具，转double
     * @param value
     * @param defaultValue
     * @return
     */
    public final static double convertToDouble(Object value, double defaultValue) {
        if(value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return Double.valueOf(value.toString());
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 安全类型转换工具，转String
     * @param value
     * @param defaultValue
     * @return
     */
    public final static String convertToString(Object value, String defaultValue) {
        if(value == null || "".equals(value.toString().trim())) {
            return defaultValue;
        }
        try {
            return value.toString();
        } catch (Exception e) {
            return defaultValue;
        }
    }

}
