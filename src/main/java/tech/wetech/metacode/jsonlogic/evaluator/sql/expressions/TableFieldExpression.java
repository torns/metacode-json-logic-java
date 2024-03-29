package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;

/**
 * @author cjbi
 * @date 2022/11/7
 */
public class TableFieldExpression implements SqlRenderExpression {

    public static final TableFieldExpression INSTANCE = new TableFieldExpression();

    @Override
    public String key() {
        return "table_field";
    }

    @Override
    public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
        return evaluator.evaluate(arguments.get(0), data) + "." + evaluator.evaluate(arguments.get(1), data);
    }
}
