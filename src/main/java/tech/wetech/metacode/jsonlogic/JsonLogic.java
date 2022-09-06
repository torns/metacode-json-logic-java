package tech.wetech.metacode.jsonlogic;

import java.lang.reflect.Array;
import java.util.Collection;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class JsonLogic {

    public static boolean truthy(Object value) {
        if (value == null) {
            return false;
        }

        if (value instanceof Boolean) {
            return (boolean) value;
        }

        if (value instanceof Number) {
            if (value instanceof Double) {
                Double d = (Double) value;

                if (d.isNaN()) {
                    return false;
                } else if (d.isInfinite()) {
                    return true;
                }
            }

            if (value instanceof Float) {
                Float f = (Float) value;

                if (f.isNaN()) {
                    return false;
                } else if (f.isInfinite()) {
                    return true;
                }
            }

            return ((Number) value).doubleValue() != 0.0;
        }

        if (value instanceof String) {
            return !((String) value).isEmpty();
        }

        if (value instanceof Collection) {
            return !((Collection) value).isEmpty();
        }

        if (value.getClass().isArray()) {
            return Array.getLength(value) > 0;
        }

        return true;
    }

    public static boolean isEligible(Object data) {
        return data != null && (data instanceof Iterable || data.getClass().isArray());
    }

}
