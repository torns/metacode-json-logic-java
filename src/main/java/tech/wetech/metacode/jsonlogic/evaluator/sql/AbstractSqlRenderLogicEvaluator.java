package tech.wetech.metacode.jsonlogic.evaluator.sql;

import tech.wetech.metacode.jsonlogic.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.expressions.AssignmentAndComparisonSqlRenderExpression;
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

    protected AbstractSqlRenderLogicEvaluator(JsonLogicNode root) {
        this.root = root;

        addOperation(LogicSqlRenderExpression.AND);
        addOperation(LogicSqlRenderExpression.OR);

        addOperation(AssignmentAndComparisonSqlRenderExpression.EQ);
        addOperation(AssignmentAndComparisonSqlRenderExpression.NE);
        addOperation(AssignmentAndComparisonSqlRenderExpression.GT);
        addOperation(AssignmentAndComparisonSqlRenderExpression.GTE);
        addOperation(AssignmentAndComparisonSqlRenderExpression.LT);
        addOperation(AssignmentAndComparisonSqlRenderExpression.LTE);
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

    @Override
    public List<JsonLogicExpression> getExpressions() {
        return expressions;
    }

}
