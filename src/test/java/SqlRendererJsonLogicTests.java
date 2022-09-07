import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.JsonLogicParser;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.NamedSqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.SqlRenderLogicEvaluator;
import tech.wetech.metacode.jsonlogic.evaluator.sql.IndexSqlRenderResult;
import tech.wetech.metacode.jsonlogic.evaluator.sql.NamedSqlRenderResult;

/**
 * @author cjbi
 * @date 2022/9/5
 */
public class SqlRendererJsonLogicTests {

    @Test
    void testIndex() throws JsonLogicException {
        //{"and":[{">":[{"table_field":["user","id"]}, 2]},{"==":["jack", {"table_field":["user","name"]}]},{"<":[{"table_field":["user","age"]},21]}]}
        JsonLogicNode logicNode = JsonLogicParser.parse("{\"and\":[{\">\":[{\"table_field\":[\"user\",\"id\"]}, 2]},{\"==\":[\"jack\", {\"table_field\":[\"user\",\"name\"]}]},{\"<\":[{\"table_field\":[\"user\",\"age\"]},21]}]}");
        IndexSqlRenderResult renderResult = logicNode.evaluator(SqlRenderLogicEvaluator::new).evaluate();
        System.out.println(renderResult.whereClause());
        for (Object arg : renderResult.args()) {
            System.out.println(arg);
        }
    }

    @Test
    void testNamed() throws JsonLogicException {
//{"and":[{">":[{"table_field":["id",3]}, 2]},{"==":["jack", {"table_field":"user.name"}]},{"<":[{"table_field":"user.age"},21]}]}
        JsonLogicNode logicNode = JsonLogicParser.parse("{ \"or\": [ { \"and\": [ { \">\": [ { \"table_field\": [ \"user\", \"id\" ] }, 2 ] }, { \"==\": [ \"jack\", { \"table_field\": [ \"user\", \"name\" ] } ] }, { \"<\": [ { \"table_field\": [ \"user\", \"age\" ] }, 21 ] } ] }, { \"and\": [ { \">\": [ { \"table_field\": [ \"user\", \"id\" ] }, 2 ] }, { \"==\": [ \"jack\", { \"table_field\": [ \"user\", \"name\" ] } ] }, { \"<\": [ { \"table_field\": [ \"user\", \"age\" ] }, 21 ] } ] }, { \"and\": [ { \">\": [ { \"var\": [ \"id\", 3 ] }, 2 ] }, { \"==\": [ \"mark\", { \"table_field\": [ \"user\", \"name\" ] } ] }, { \"==\": [ 1, 1 ] } ] } ] }");
        NamedSqlRenderResult renderResult = logicNode.evaluator(NamedSqlRenderLogicEvaluator::new).evaluate();
        System.out.println(renderResult.whereClause());
        renderResult.args().forEach((key, value) -> System.out.println(key + ": " + value));
    }


}
