import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.sessionId;

public class JiraAPI {
    public static void main(String[] args) {
        RestAssured.baseURI = "http://localhost:8080";

//        LOGIN
        SessionFilter session = new SessionFilter();
        String resp = given().header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\":\"himanshu082001\",\n" +
                        "    \"password\":\"Killvibes@18\"\n" +
                        "}").log().all().filter(session)
                .when().post("/rest/auth/1/session")
                .then().log().all().extract().response().asString();

//        Add Comment
       String addComment =  given().pathParam("id", "10004").log().all()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"Adding comment through REST API\",\n" +
                        "    \"visibility\": {\n" +
                        "        \"type\": \"role\",\n" +
                        "        \"value\": \"Administrators\"\n" +
                        "    }\n" +
                        "}").filter(session)
                .when().post("/rest/api/2/issue/{id}/comment")
                .then().log().all().assertThat().statusCode(201).extract().response().asString();

JsonPath js = new JsonPath(addComment);
String commentID = js.getString("id");

//        Add attachment
//        given().header("X-Atlassian-Token", "no-check").filter(session)
//                .pathParam("id", "10004")
//                .header("Content-Type","multipart/form-data")
//                .multiPart("file", new File("jira.txt "))
//                .when().post("/rest/api/2/issue/{id}/attachments")
//                .then().log().all().assertThat().statusCode(200);


//        Get issue
        String issueDetails = given().filter(session).pathParam("id","10004")
                .queryParam("fields","comment")
                .get("/rest/api/2/issue/{id}").then().log().all().extract()
                .response().asString();

        System.out.println(issueDetails);

        JsonPath js1 =new JsonPath(issueDetails);
        int commentsCount = js1.get("fields.comment.comments.size()");

        for(int i=0;i<commentsCount;i++){
            if(js1.getString("fields.comment.comments["+i+"].id").equalsIgnoreCase(commentID)){
                if(js1.getString("fields.comment.comments["+i+"].body").equalsIgnoreCase("Adding comment through REST API")){
                    System.out.println("Correct comment added");
                }else{
                    System.out.println("Incorrect comment added");break;
                }
            }
        }
    }
}
