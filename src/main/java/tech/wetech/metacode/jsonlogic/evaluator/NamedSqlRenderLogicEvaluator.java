package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.AbstractSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedPlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class NamedSqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {

    public NamedSqlRenderLogicEvaluator(JsonLogicNode root) {
        super(root, new NamedPlaceholderHandler());
    }

    public NamedSqlRenderResult evaluate() throws JsonLogicEvaluationException {
        Object sql = evaluate((JsonLogicOperation) root, null);
        placeholderHandler.getParameters();
        return new NamedSqlRenderResult((String) sql, (Map<String, Object>) placeholderHandler.getParameters());
    }

    @Override
    public Object evaluate(JsonLogicNode node, Object data) throws JsonLogicEvaluationException {
        switch (node.getType()) {
            case PRIMITIVE:
                return evaluate((JsonLogicPrimitive) node, data);
            case VARIABLE:
                return evaluate((JsonLogicVariable) node, data);
            case ARRAY:
                return evaluate((JsonLogicArray) node, data);
            default:
                return evaluate((JsonLogicOperation) node, data);
        }
    }

    public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
        JsonLogicExpression expression = getExpression(operation.getOperator());
        return expression.evaluate(this, operation.getArguments(), placeholderHandler);

    }

}
