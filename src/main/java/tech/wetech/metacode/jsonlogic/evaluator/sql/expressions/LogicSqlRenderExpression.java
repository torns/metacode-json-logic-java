package tech.wetech.metacode.jsonlogic.evaluator.sql.expressions;

import tech.wetech.metacode.jsonlogic.ast.JsonLogicArray;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluationException;
import tech.wetech.metacode.jsonlogic.evaluator.JsonLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.PlaceholderHandler;
import tech.wetech.metacode.jsonlogic.evaluator.sql.SqlRenderResult;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cjbi
 * @date 2022/9/6
 */
public class LogicSqlRenderExpression implements SqlRenderExpression {

    public static final LogicSqlRenderExpression AND = new LogicSqlRenderExpression(true);
    public static final LogicSqlRenderExpression OR = new LogicSqlRenderExpression(false);

    private final boolean isAnd;

    private LogicSqlRenderExpression(boolean isAnd) {
        this.isAnd = isAnd;
    }

    @Override
    public String key() {
        return isAnd ? "and" : "or";
    }

    @Override
    public <T extends JsonLogicEvaluator> String evaluate(T evaluator, JsonLogicArray arguments, Object data) throws JsonLogicEvaluationException {
        PlaceholderHandler placeholderHandler = (PlaceholderHandler) data;
        if (arguments.size() < 1) {
            throw new JsonLogicEvaluationException("and operator expects at least 1 argument");
        }
        List<SqlRenderResult> list = new ArrayList<>();
        for (JsonLogicNode element : arguments) {
            list.add((SqlRenderResult) evaluator.evaluate(element, placeholderHandler));
        }
        String whereClause = list.stream()
            .map(SqlRenderResult::whereClause)
            .collect(Collectors.joining(" " + key() + " ", " (", " )"));
        return whereClause;
    }
}
