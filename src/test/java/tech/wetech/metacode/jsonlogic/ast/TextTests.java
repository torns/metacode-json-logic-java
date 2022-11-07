package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.BooleanLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class TextTests {

    @Test
    void testTableField() throws JsonLogicException {
        String json = """
            {
              "==": [
                { "table_field": ["defaultvaluetest", "wenben1"] },
                { "table_field": ["defaultvaluetest", "wenben1"] }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" defaultvaluetest.wenben1 = defaultvaluetest.wenben1", result.whereClause());
    }

    @Test
    void testContains() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "contains": [{ "table_field": ["defaultvaluetest", "wenben1"] }, "二十大"]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" ( defaultvaluetest.wenben1 like concat('%', concat(:defaultvaluetest_wenben1_0,'%')) )", result.whereClause());
        assertTrue(result.args().values().contains("二十大"));
        assertTrue(JsonLogic.apply(json, BooleanLogicEvaluator::new).evaluate(Map.of("defaultvaluetest", Map.of("wenben1", "党的二十大"))));
    }

    @Test
    void testNotContains() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  "not_contains": [{ "table_field": ["defaultvaluetest", "wenben1"] }, "二十大"]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertEquals(" ( defaultvaluetest.wenben1 not like concat('%', concat(:defaultvaluetest_wenben1_0,'%')) )", result.whereClause());
        assertTrue(result.args().containsValue("二十大"));
        assertFalse(JsonLogic.apply(json, BooleanLogicEvaluator::new).evaluate(Map.of("defaultvaluetest", Map.of("wenben1", "党的二十大"))));
    }

}
