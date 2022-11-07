package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class DatetimeTests {


    @Test
    void testComparison() throws JsonLogicException {
        String json = """
             {
               "==": [
                 { "table_field": ["defaultvaluetest", "riqishijian3"] },
                 { "datetime": "2022-11-06T20:06:34.000" }
               ]
             }
            """;
        NamedSqlRenderResult result = JsonLogic.apply(json, NamedSqlRenderLogicEvaluator::new).evaluate();
        String sql = result.whereClause();
        for (Map.Entry<String, Object> entry : result.args().entrySet()) {
            sql = sql.replace(":" + entry.getKey(), "'" + entry.getValue().toString() + "'");
        }
        assertEquals(" defaultvaluetest.riqishijian3 = '2022-11-06T20:06:34'", sql);
    }

}
