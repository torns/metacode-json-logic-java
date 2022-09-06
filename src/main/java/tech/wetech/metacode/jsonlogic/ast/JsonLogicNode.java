package tech.wetech.metacode.jsonlogic.ast;

import tech.wetech.metacode.jsonlogic.JsonLogicEvaluator;

import java.util.function.Function;

/**
 * @author cjbi
 * @date 2022/9/4
 */
public interface JsonLogicNode {
    JsonLogicNodeType getType();

    default <R extends JsonLogicEvaluator> R evaluator(Function<JsonLogicNode, R> function) {
        return function.apply(this);
    }
}
