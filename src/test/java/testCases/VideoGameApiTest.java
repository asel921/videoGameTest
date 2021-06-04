package testCases;

import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matcher.*;
import static org.hamcrest.Matchers.equalTo;

public class VideoGameApiTest {
  int id = 13;

  @Test(priority = 1)
  public void test_getAllVideoGames(){
    given()
            .when()
                .get("http://localhost:8080/app/videogames")
            .then()
              .statusCode(200);

  }
   @Test(priority = 2)
  public void test_addNewVideoGame(){
    HashMap data = new HashMap();
    data.put("id", id);
    data.put("name", "Spider-Man");
    data.put("releaseDate", "2019-09-20T08:55:58.510Z");
    data.put("reviewScore", "5");
    data.put("category", "Adventure");
    data.put("rating", "Universal");

    Response res =
    given()
            .contentType("application/json")
            .body(data)
      .when()
            .post("http://localhost:8080/app/videogames")
      .then()
             .statusCode(200)
             .log().body()
             .extract().response();


    String jsonString = res.asString();
    Assert.assertEquals(jsonString.contains("Record Added Successfully"), true);
  }

  @Test(priority = 3)
  public void test_getVideoGame(){
    given()
            .when()
              .get("http://localhost:8080/app/videogames/" + id)
            .then()
            .statusCode(200)
            .log().body() //xml body log to terminal
            .body("videoGame.id", equalTo("" + id))
            .body("videoGame.name", equalTo("Spider-Man"))
            .body("videoGame.releaseDate", equalTo("2019-09-20T00:00:00-05:00"))
            .body("videoGame.reviewScore", equalTo("5"));
  }


    @Test(priority = 4)
  public void test_UpdateVideoGame(){
    HashMap data = new HashMap();
    data.put("id", id);
    data.put("name", "Iron Man");
    data.put("releaseDate", "2019-09-20T08:55:58.510Z");
    data.put("reviewScore", "4");
    data.put("category", "Adventure");
    data.put("rating", "Universal");


    given ()
            .contentType("application/json")
            .body(data)
         .when()
            .put("http://localhost:8080/app/videogames/" + id)
          .then()
             .statusCode(200)
            .log().body()
            .body("videoGame.id", equalTo("" + id))
            .body("videoGame.name", equalTo("Iron Man"));
  }

  @Test(priority = 5)
  public void test_DeleteVideoGame(){
    Response res =
    given()
            .when()
              .delete("http://localhost:8080/app/videogames/" + id)
            .then()
               .statusCode(200)
            .log().body()
            .extract().response();

    String jsonString = res.asString();
    Assert.assertEquals(jsonString.contains("Record Deleted "), true);
  }

}
