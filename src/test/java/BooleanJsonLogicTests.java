import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.JsonLogicException;
import tech.wetech.metacode.jsonlogic.JsonLogicParser;
import tech.wetech.metacode.jsonlogic.ast.JsonLogicNode;
import tech.wetech.metacode.jsonlogic.evaluator.BooleanLogicEvaluator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author cjbi
 * @date 2022/9/4
 */
public class BooleanJsonLogicTests {

    @Test
    void testOr() throws JsonLogicException {
        JsonLogicNode logicNode = JsonLogicParser.parse("{\"or\":[{\">\":[1, 2]},{\"<\":[1, 2]}]}");
        assertTrue(logicNode.evaluator(BooleanLogicEvaluator::new).evaluate(null));
    }

    @Test
    public void testAnd() throws JsonLogicException {
        JsonLogicNode logicNode = JsonLogicParser.parse("{\"and\":[{\">\":[3, 2]},{\"<\":[1, 2]}]}");
        assertTrue(logicNode.evaluator(BooleanLogicEvaluator::new).evaluate(null));
    }

    @Test
    void testAndOr() throws JsonLogicException {
        JsonLogicNode logicNode = JsonLogicParser.parse("{\"and\":[{\"and\":[{\">=\":[3, 5]},{\"<=\":[1, 2]}]},{\"or\":[{\">\":[3, 2]},{\"<\":[1, 2]}]}]}");
        assertFalse(logicNode.evaluator(BooleanLogicEvaluator::new)
            .evaluate(null));
    }

    @Test
    void testVar() throws JsonLogicException {

        Map<String, Object> data = new HashMap<>();
        data.put("b", 10);
        data.put("c", Collections.singletonMap("cc", 20));
        JsonLogicNode logicNode = JsonLogicParser.parse("{\"and\":[{\">\":[{\"var\":[\"a\",3]}, 2]},{\"<\":[1, {\"var\":\"b\"}]},{\"<\":[{\"var\":\"c.cc\"},21]}]}");
        assertTrue(logicNode.evaluator(BooleanLogicEvaluator::new)
            .evaluate(data));
    }

}
