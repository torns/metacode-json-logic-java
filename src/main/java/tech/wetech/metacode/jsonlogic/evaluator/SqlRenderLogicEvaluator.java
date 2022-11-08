package tech.wetech.metacode.jsonlogic.evaluator;

import tech.wetech.metacode.jsonlogic.ast.*;
import tech.wetech.metacode.jsonlogic.evaluator.sql.AbstractSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexPlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRenderLogicEvaluator extends AbstractSqlRenderLogicEvaluator {


    public SqlRenderLogicEvaluator(JsonLogicNode root) {
        super(root, new IndexPlaceholderHandler());
    }

    public IndexSqlRenderResult evaluate() throws JsonLogicEvaluationException {
        return new IndexSqlRenderResult((String) evaluate((JsonLogicOperation) root, null), (Object[]) placeholderHandler.getParameters());
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
                return this.evaluate((JsonLogicOperation) node, data);
        }
    }

    public Object evaluate(JsonLogicOperation operation, Object data) throws JsonLogicEvaluationException {
        JsonLogicExpression expression = getExpression(operation.getOperator());
        return expression.evaluate(this, operation.getArguments(), placeholderHandler);
    }
}
