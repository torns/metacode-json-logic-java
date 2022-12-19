package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogic;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class DatetimeTests {

    private static final JsonLogic jsonLogic = new JsonLogic();

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
        NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
        String sql = result.whereClause();
        for (Map.Entry<String, Object> entry : result.args().entrySet()) {
            sql = sql.replace(":" + entry.getKey(), "'" + entry.getValue().toString() + "'");
        }
        assertEquals(" defaultvaluetest.riqishijian3 = '2022-11-06T20:06:34'", sql);
    }

    @Test
    void testRange() throws JsonLogicException {
        String json = """
            {
              "and": [
                {
                  ">=": [
                    { "table_field": ["testaaaa", "huiyikaishishijian"] },
                    { "datetime": "2022-10-01T10:45:12.560" }
                  ]
                },
                {
                  "<=": [
                    { "table_field": ["testaaaa", "huiyijieshushijian"] },
                    { "datetime": "2022-10-31T10:45:12.560" }
                  ]
                }
              ]
            }
            """;
        NamedSqlRenderResult result = jsonLogic.evaluateNamedSql(json);
        System.out.println(result);
    }

}
