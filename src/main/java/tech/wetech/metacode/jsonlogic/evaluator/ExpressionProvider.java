package tech.wetech.metacode.jsonlogic.evaluator;

import java.util.List;

/**
 * @author cjbi
 * @date 2022/12/18
 */
public interface ExpressionProvider {

    List<JsonLogicExpression> getExpressions(Class<? extends JsonLogicEvaluator> evaluatorType);

}
