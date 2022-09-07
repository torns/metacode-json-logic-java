package tech.wetech.metacode.jsonlogic.evaluator.expressions;

import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;

import java.util.List;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class NumericComparisonExpression implements PreEvaluatedArgumentsExpression {

    public static final NumericComparisonExpression GT = new NumericComparisonExpression(">");
    public static final NumericComparisonExpression GTE = new NumericComparisonExpression(">=");
    public static final NumericComparisonExpression LT = new NumericComparisonExpression("<");
    public static final NumericComparisonExpression LTE = new NumericComparisonExpression("<=");
    private final String key;

    private NumericComparisonExpression(String key) {
        this.key = key;
    }

    @Override
    public String key() {
        return key;
    }

    @Override
    public Boolean evaluate(List arguments, Object data) throws JsonLogicEvaluationException {
        int n = arguments.size();
        if (n != 2) {
            throw new JsonLogicEvaluationException("'" + key + "' requires at least 2 arguments");
        }
        double[] values = new double[n];

        for (int i = 0; i < n; i++) {
            Object value = arguments.get(i);

            if (value instanceof String) {
                try {
                    values[i] = Double.parseDouble((String) value);
                } catch (NumberFormatException e) {
                    return false;
                }
            } else if (!(value instanceof Number)) {
                return false;
            } else {
                values[i] = ((Number) value).doubleValue();
            }
        }
        // Handle regular comparisons
        switch (key) {
            case "<":
                return values[0] < values[1];

            case "<=":
                return values[0] <= values[1];

            case ">":
                return values[0] > values[1];

            case ">=":
                return values[0] >= values[1];

            default:
                throw new JsonLogicEvaluationException("'" + key + "' is not a comparison expression");
        }
    }
}
