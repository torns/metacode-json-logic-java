package tech.wetech.metacode.jsonlogic;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicParser;
import tech.wetech.metacode.jsonlogic.evaluator.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class JsonLogic {

    private final Map<Class<? extends JsonLogicEvaluator>, JsonLogicEvaluator> evaluators = new HashMap<>();

    public JsonLogic() {
        evaluators.put(BooleanLogicEvaluator.class, new BooleanLogicEvaluator());
        evaluators.put(NumberJsonLogicEvaluator.class, new NumberJsonLogicEvaluator());
        evaluators.put(NamedSqlRenderLogicEvaluator.class, new NamedSqlRenderLogicEvaluator());
        evaluators.put(SqlRenderLogicEvaluator.class, new SqlRenderLogicEvaluator());
    }

    public boolean evaluateBoolean(String json, Object data) throws JsonLogicException {
        return (boolean) evaluators.get(BooleanLogicEvaluator.class).evaluate(JsonLogicParser.parse(json), data);
    }

    public Number evaluateNumber(String json, Object data) throws JsonLogicException {
        return (Number) evaluators.get(NumberJsonLogicEvaluator.class).evaluate(JsonLogicParser.parse(json), data);
    }

    public NamedSqlRenderResult evaluateNamedSql(String json) throws JsonLogicException {
        return ((NamedSqlRenderLogicEvaluator) evaluators.get(NamedSqlRenderLogicEvaluator.class)).evaluate(JsonLogicParser.parse(json));
    }

    public IndexSqlRenderResult evaluateIndexSql(String json) throws JsonLogicException {
        return ((SqlRenderLogicEvaluator) evaluators.get(SqlRenderLogicEvaluator.class)).evaluate(JsonLogicParser.parse(json));
    }

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
