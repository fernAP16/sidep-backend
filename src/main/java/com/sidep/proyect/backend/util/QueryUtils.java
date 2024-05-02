package com.sidep.proyect.backend.util;

import java.math.BigDecimal;
import java.math.BigInteger;

public final class QueryUtils {

    private QueryUtils() {
    }

    public static String getAsString(Object object) {
        return object != null ? object.toString() : null;
    }

    public static Integer getAsInteger(Object object) {
        if (object != null) {
            if (object instanceof Integer) {
                return (Integer) object;
            } else if (object instanceof BigInteger) {
                return ((BigInteger) object).intValue();
            } else if (object instanceof Byte) {
                return ((Byte) object).intValue();
            }
        }
        return null;
    }

    public static Long getAsLong(Object object) {
        Integer value = getAsInteger(object);
        if (value != null) {
            return Long.valueOf(value);
        }
        return null;
    }

    public static Double getAsDouble(Object object) {
        if (object != null) {
            return ((BigDecimal) object).doubleValue();
        }
        return null;
    }

}
