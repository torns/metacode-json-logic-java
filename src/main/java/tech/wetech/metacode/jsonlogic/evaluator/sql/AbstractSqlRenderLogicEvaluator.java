package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.ComparisonSqlRenderExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.ContainsExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.LogicSqlRenderExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public abstract class AbstractSqlRenderLogicEvaluator implements JsonLogicEvaluator {

    protected final JsonLogicNode root;

    protected final List<JsonLogicExpression> expressions = new ArrayList<>();

    protected final PlaceholderHandler placeholderHandler;

    protected AbstractSqlRenderLogicEvaluator(JsonLogicNode root, PlaceholderHandler placeholderHandler) {
        this.placeholderHandler = placeholderHandler;
        this.root = root;

        addOperation(LogicSqlRenderExpression.AND);
        addOperation(LogicSqlRenderExpression.OR);

        addOperation(ComparisonSqlRenderExpression.EQ);
        addOperation(ComparisonSqlRenderExpression.NE);
        addOperation(ComparisonSqlRenderExpression.GT);
        addOperation(ComparisonSqlRenderExpression.GTE);
        addOperation(ComparisonSqlRenderExpression.LT);
        addOperation(ComparisonSqlRenderExpression.LTE);

        addOperation(ContainsExpression.CONTAINS);
        addOperation(ContainsExpression.NOT_CONTAINS);
    }

    public Object evaluate(JsonLogicPrimitive<?> primitive, Object data) {
        switch (primitive.getPrimitiveType()) {
            case NUMBER:
                return ((JsonLogicNumber) primitive).getValue();

            default:
                return primitive.getValue();
        }
    }

    public Object evaluate(JsonLogicTableField tableField, Object data) {
        String table = (String) evaluate((JsonLogicPrimitive<?>) tableField.getTable(), data);
        String field = (String) evaluate((JsonLogicPrimitive<?>) tableField.getField(), data);
        return table + "." + field;
    }

    public Object evaluate(JsonLogicVariable variable, Object data) {
        return evaluate((JsonLogicPrimitive) variable.getKey(), data);
    }

    public List<Object> evaluate(JsonLogicArray array, Object data) throws JsonLogicEvaluationException {
        List<Object> values = new ArrayList<>(array.size());

        for(JsonLogicNode element : array) {
            values.add(evaluate(element, data));
        }
        return values;
    }

    @Override
    public List<JsonLogicExpression> getExpressions() {
        return expressions;
    }

    protected PlaceholderHandler getPlaceholderHandler() {
        return placeholderHandler;
    }
}
