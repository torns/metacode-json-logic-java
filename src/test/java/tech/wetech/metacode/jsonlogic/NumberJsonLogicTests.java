package tech.wetech.metacode.jsonlogic;

import org.junit.jupiter.api.Test;
import tech.wetech.metacode.jsonlogic.evaluator.NumberJsonLogicEvaluator;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

/**
 * @author cjbi
 * @date 2022/12/17
 */
public class NumberJsonLogicTests {

    @Test
    void testAdd() throws JsonLogicException {
        String json = """
                {"+":[4,2]}
            """;
        //4+2=6
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);
        assertEquals(6.0, result);
    }

    @Test
    void testMultiAdd() throws JsonLogicException {
        String json = """
                {"+":[2,2,2,2,2]}
            """;
        //2+2+2+2+2=10
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);
        assertEquals(10.0, result);
    }

    @Test
    void testSingleAdd() throws JsonLogicException {
        String json = "{\"+\":[2,2,2,2,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);
        assertEquals(10.0, result);
    }


    @Test
    void testSubtract() throws JsonLogicException {
        String json = "{\"-\":[4,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(2.0, result);
    }

    @Test
    void testSingleSubtract() throws JsonLogicException {
        String json = "{\"-\": 2 }";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(-2.0, result);
    }

    @Test
    void testMultiply() throws JsonLogicException {
        String json = "{\"*\":[4,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(8.0, result);
    }

    @Test
    void testMultiMultiply() throws JsonLogicException {
        String json = "{\"*\":[2,2,2,2,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(32.0, result);
    }

    @Test
    void testDivide() throws JsonLogicException {
        String json = "{\"/\":[4,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(2.0, result);
    }

    @Test
    void testDivideBy0() throws JsonLogicException {
        String json = "{\"/\":[4,0]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(Double.POSITIVE_INFINITY, result);
    }

    @Test
    void testModulo() throws JsonLogicException {
        String json = "{\"%\": [101,2]}";
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);

        assertEquals(1.0, result);
    }

//    @Test
//    void testMin() throws JsonLogicException {
//        String json = "{\"min\":[1,2,3]}";
//        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);
//
//        assertEquals(1.0, result);
//    }

//    @Test
//    void testMax() throws JsonLogicException {
//        String json = "{\"max\":[1,2,3]}";
//        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(null);
//
//        assertEquals(3.0, result);
//    }

    @Test
    void testDivideSingleNumber() throws JsonLogicException {
        assertNull(JsonLogic.apply("{\"/\": [0]}", NumberJsonLogicEvaluator::new).evaluate(null));
    }

    @Test
    void testVar() throws JsonLogicException {
        String json = """
            {"+":[2,{"var":"a"},{"var":"b"}]}
            """;
        Map<String, String> data = Map.of("a", "5", "b", "3");
        Number result = JsonLogic.apply(json, NumberJsonLogicEvaluator::new).evaluate(data);
        assertEquals(10.0, result);
    }



}
