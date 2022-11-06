package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicPrimitive;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicTableField;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicExpression;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public interface SqlRenderExpression extends JsonLogicExpression {

    String TRUE = "1=1";
    String FALSE = "1<>1";

    default <T extends JsonLogicEvaluator> Object handle(T evaluator, Object data, PlaceholderHandler placeholderHandler, Object right, String field) throws JsonLogicEvaluationException {
        if (right instanceof JsonLogicNode node) {
            return right instanceof JsonLogicPrimitive<?> ? placeholderHandler.handle(field, evaluator.evaluate(node, data)) : evaluator.evaluate(node, data);
        }
        return right;
    }

    default String getAlias(JsonLogicEvaluator evaluator, Object element) throws JsonLogicEvaluationException {
        if (element instanceof JsonLogicTableField tableField) {
            return (String) evaluator.evaluate(tableField, null);
        }
        return Integer.toHexString(element.hashCode());
    }

}
