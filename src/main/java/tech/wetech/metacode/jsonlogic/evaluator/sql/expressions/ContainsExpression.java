package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class ContainsExpression implements SqlRenderExpression {

    public static final ContainsExpression CONTAINS = new ContainsExpression(false);

    public static final ContainsExpression NOT_CONTAINS = new ContainsExpression(true);

    private final boolean isNot;

    public ContainsExpression(boolean isNot) {
        this.isNot = isNot;
    }

    @Override
    public String key() {
        return isNot ? "not_contains" : "contains";
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends JsonLogicEvaluator> Object evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
        JsonLogicNode left = arguments.get(0);
        JsonLogicNode right = arguments.get(1);
        if (right instanceof List list) {
            if (list.isEmpty()) {
                return TRUE;
            }
            return list.stream()
                .map(element -> getSingle(evaluator, data, left, element))
                .collect(Collectors.joining(" and ", " (", ") "));
        }
        return getSingle(evaluator, data, left, right);
    }

    public String getSingle(JsonLogicEvaluator evaluator, Object data, Object left, Object right) {
        try {
            PlaceholderHandler placeholderHandler = (PlaceholderHandler) data;
            StringBuilder sb = new StringBuilder(" ");
            sb.append(handle(evaluator, data, placeholderHandler, left, getAlias(evaluator, right)));
            sb.append(" ");
            sb.append(isNot ? "not like" : "like");
            sb.append(" ");
            sb.append("concat('%', concat(").append(handle(evaluator, data, placeholderHandler, right, getAlias(evaluator, left))).append(",'%'))");
            return sb.toString();
        } catch (JsonLogicEvaluationException e) {
            return TRUE;
        }
    }

}
