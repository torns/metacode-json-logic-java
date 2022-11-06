package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class MultipleTests {

    @Test
    void testContains() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "contains": [
                    { "table_field": ["defaultvaluetest", "duoxuan2"] },
                    ["天空","大地"]
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        Assertions.assertEquals(" ( ( defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_0,'%')) and  defaultvaluetest.duoxuan2 like concat('%', concat(:defaultvaluetest_duoxuan2_1,'%')))  )",result.whereClause());
    }

}
