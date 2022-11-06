package tech.wetech.metacode.jsonlogic;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.SqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRendererJsonLogicTests {

    @Test
    void testIndex() throws JsonLogicException {
        String json = """
            {
              "and": [
                { ">": [{ "table_field": ["user", "id"] }, 2] },
                { "==": ["jack", { "table_field": ["user", "name"] }] },
                { "<": [{ "table_field": ["user", "age"] }, 21] }
              ]
            }
            """;
        IndexSqlRenderResult renderResult = JsonLogic.apply(json, SqlRenderLogicEvaluator::new).evaluate();
        assertNotNull(renderResult.whereClause());
        assertEquals(3, renderResult.args().length);
    }

    @Test
    void testNamed() throws JsonLogicException {
        String json = """
            {
              "or": [
                {
                  "and": [
                    { ">": [{ "table_field": ["user", "id"] }, 2] },
                    { "==": ["jack", { "table_field": ["user", "name"] }] },
                    { "<": [{ "table_field": ["user", "age"] }, 21] }
                  ]
                },
                {
                  "and": [
                    { ">": [{ "table_field": ["user", "id"] }, 2] },
                    { "==": ["jack", { "table_field": ["user", "name"] }] },
                    { "<": [{ "table_field": ["user", "age"] }, 21] }
                  ]
                },
                {
                  "and": [
                    { ">": [{ "var": ["id", 3] }, 2] },
                    { "==": ["mark", { "table_field": ["user", "name"] }] },
                    { "==": [1, 1] }
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult renderResult = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        assertNotNull(renderResult.whereClause());
        assertEquals(10, renderResult.args().size());
    }


}
