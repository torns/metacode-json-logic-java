package tech.wetech.metacode.jsonlogic.ast;

import org.junit.jupiter.api.Test;

/**
 * @author cjbi
 * @date 2022/11/6
 */
public class DatetimeTests {


    @Test
    void testComparison(){
        String json = """
             {
               "==": [
                 { "table_field": ["defaultvaluetest", "riqishijian3"] },
                 { "datetime": "2022-11-06T20:06:34.000" }
               ]
             }
            """;

    }

}
