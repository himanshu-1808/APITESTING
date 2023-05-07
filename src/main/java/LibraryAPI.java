import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class LibraryAPI {
    @Test(dataProvider = "BooksData")
    public static void main(String isbn, String aisle) {
        RestAssured.baseURI = "http://216.10.245.166";


        String resp = given().header("Content-Type", "application/json")
                .body(payLoad.addBook("jdjj", "48u3"))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = GenericUtils.getJSONPath(resp);
        String id = js.get("ID");
        System.out.println(id);
    }

    @DataProvider(name = "BooksData")
    public static Object[][] addBookData() {
        return new Object[][]{{"3453", "dvkhjbfv"}, {"3867308", "dvhaid"}, {"539fs", "uvhh"}};
    }
}
