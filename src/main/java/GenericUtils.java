import io.restassured.path.json.JsonPath;

public class GenericUtils {
    public static String extractKeyValueFromResp(String resp, String key) {
        JsonPath js = new JsonPath(resp);     //Will convert it into JSON
        return js.getString(key);
    }
    public static JsonPath getJSONPath(String resp){
        return new JsonPath(resp);
    }
}
