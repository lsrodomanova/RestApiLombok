package lubl;

import models.RequestCreate;
import models.UserData;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static lubl.Specs.*;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ApiTests {


    String
            body = "{\"name\": \"morpheus\",\n" +
            "    \"job\": \"leader\"}",
            body2 ="{\"email\": \"sydney@fife\"}",
            name="morpheus",
            job="leader";


    @Test
    public void getSingleUser() {
        given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpec)
                .extract().response();
    }

    @Test
    void userNotFound() {
        given()
                .spec(request)
                .when()
                .get("/users/23")
                .then()
                .spec(response404)
                .log().body()
                .body(is("{}"));
    }

    @Test
    public void getSingleResource() {
        given()
                .spec(request)
                .get("/unknown/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract().response();
    }

    @Test
    void createUser() {
        UserData data= given()
                .spec(request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .spec(response201)
                .extract().as(UserData.class);
        assertEquals(name, data.getName());
        assertEquals(job, data.getJob());
    }

    @Test
    void deleteUser() {
        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then()
                .spec(response204);
    }

    @Test
    void registerUserUnsuccesfull() {
        given()
                .spec(request)
                .body(body2)
                .when()
                .post("/register")
                .then()
                .spec(response400);
    }

    @Test
    public void checkEmailUsingGroovy() {
        // @formatter:off
        given()
                .spec(request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("eve.holt@reqres.in"));
        // @formatter:on
    }

    @Test
    void createNewUser() {
        RequestCreate requestCreate = new RequestCreate();
        requestCreate.setJob("leader");
        requestCreate.setName("morpheus");
        UserData data= given()
                        .spec(request)
                        .body(requestCreate)
                        .when()
                .post("/users")
                .then()
                .spec(response201)
                .extract().as(UserData.class);
        assertEquals(name, data.getName());
        assertEquals(job, data.getJob());
    }
}
