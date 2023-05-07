import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import java.sql.Date;
import java.text.DateFormat;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class Basic {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String resp = given().queryParam("key", "qaclick123")
                .header("Content-Type", "application/json")
                .body(payLoad.addPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200)
                .body("scope", equalTo("APP")).extract().response().asString();


        String placeId = GenericUtils.extractKeyValueFromResp(resp, "place_id");


        //Update place
        String newAddress = "Summer walk, Africa";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
                .body(payLoad.updateLocation(placeId, newAddress))
                .when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));

        //Get place
        String getResp = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();


        String extractedAddress = GenericUtils.extractKeyValueFromResp(getResp, "address");
        Assert.assertEquals(extractedAddress, newAddress);


    }
}
