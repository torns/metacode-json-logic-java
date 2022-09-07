package tech.wetech.metacode.jsonlogic.ast;

/**
 * @author cjbi
 * @date 2022/9/7
 */
public class JsonLogicTableField implements JsonLogicNode {

    private final JsonLogicNode table;
    private final JsonLogicNode field;

    public JsonLogicTableField(JsonLogicNode table, JsonLogicNode field) {
        this.table = table;
        this.field = field;
    }

    @Override
    public JsonLogicNodeType getType() {
        return JsonLogicNodeType.TABLE_FIELD;
    }

    public JsonLogicNode getTable() {
        return table;
    }

    public JsonLogicNode getField() {
        return field;
    }
}
