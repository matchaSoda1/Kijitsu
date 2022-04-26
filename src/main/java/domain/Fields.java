package domain;
import java.util.LinkedHashMap;
import java.util.Map;

public class Fields {
    private Map<String,String> fields;

    public Fields(){
        this.fields = new LinkedHashMap<>();
    }
    public Fields(String fieldName, String fieldText){
        this();
        this.fields.put(fieldName,fieldText);
    }
    public void put(String field, String fieldText) {
        this.fields.put(field,fieldText);
    }

    public String getFieldText(String field){
      return this.fields.get(field);
    }


    public Map<String,String> getFields() {
        return this.fields;
    }

    public String toString(){
        return this.fields.toString();
    }
}
