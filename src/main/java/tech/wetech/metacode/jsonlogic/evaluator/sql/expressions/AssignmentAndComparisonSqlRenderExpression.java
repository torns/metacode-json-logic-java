package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicPrimitive;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicTableField;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class AssignmentAndComparisonSqlRenderExpression implements JsonLogicExpression {


    public static final AssignmentAndComparisonSqlRenderExpression EQ = new AssignmentAndComparisonSqlRenderExpression("==");
    public static final AssignmentAndComparisonSqlRenderExpression NE = new AssignmentAndComparisonSqlRenderExpression("!=");
    public static final AssignmentAndComparisonSqlRenderExpression GT = new AssignmentAndComparisonSqlRenderExpression(">");
    public static final AssignmentAndComparisonSqlRenderExpression GTE = new AssignmentAndComparisonSqlRenderExpression(">=");
    public static final AssignmentAndComparisonSqlRenderExpression LT = new AssignmentAndComparisonSqlRenderExpression("<");
    public static final AssignmentAndComparisonSqlRenderExpression LTE = new AssignmentAndComparisonSqlRenderExpression("<=");
    private final String key;

    private static Map<String, Object> OPERATOR_MAP = new HashMap<>();

    static {
        OPERATOR_MAP.put("==", "=");
    }

    private AssignmentAndComparisonSqlRenderExpression(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public <T extends JsonLogicEvaluator> String evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
        PlaceholderHandler placeholderHandler = (PlaceholderHandler) data;
        JsonLogicNode left = arguments.get(0);
        JsonLogicNode right = arguments.get(1);
        StringBuilder sb = new StringBuilder(" ");
        sb.append(handle(evaluator, data, placeholderHandler, left, getAlias(evaluator, right)));
        sb.append(" ");
        sb.append(OPERATOR_MAP.getOrDefault(key, key));
        sb.append(" ");
        sb.append(handle(evaluator, data, placeholderHandler, right, getAlias(evaluator, left)));
        return sb.toString();
    }


    private String getAlias(JsonLogicEvaluator evaluator, JsonLogicNode element) throws JsonLogicEvaluationException {
        if (element instanceof JsonLogicTableField tableField) {
            return (String) evaluator.evaluate(tableField, null);
        }
        return Integer.toHexString(element.hashCode());
    }

    private static <T extends JsonLogicEvaluator> Object handle(T evaluator, Object data, PlaceholderHandler placeholderHandler, JsonLogicNode right, String field) throws JsonLogicEvaluationException {
        return right instanceof JsonLogicPrimitive<?> ? placeholderHandler.handle(field, evaluator.evaluate(right, data)) : evaluator.evaluate(right, data);
    }

}
