package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.AbstractSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedPlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class NamedSqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {

    private final NamedPlaceholderHandler placeholderHandler;

    public NamedSqlRenderLogicEvaluator(JsonLogicNode root) {
        super(root);
        this.placeholderHandler = new NamedPlaceholderHandler();
    }

    public NamedSqlRenderResult evaluate() throws JsonLogicEvaluationException {
        return evaluate((JsonLogicOperation) root, null);
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
            case TABLE_FIELD:
                return evaluate((JsonLogicTableField) node, data);
            default:
                return this.evaluate((JsonLogicOperation) node, data);
        }
    }


    public NamedSqlRenderResult evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
        JsonLogicExpression expression = getExpression(operation.getOperator());
        String whereClause = (String) expression.evaluate(this, operation.getArguments(), placeholderHandler);
        return new NamedSqlRenderResult(whereClause, placeholderHandler.getParameters());
    }

}
